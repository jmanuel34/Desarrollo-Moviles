package com.curso.mitiempo_jm.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.curso.mitiempo_jm.AemetApiService
import com.curso.mitiempo_jm.BuildConfig
import com.curso.mitiempo_jm.R
import com.curso.mitiempo_jm.data_network.MunicipioItem
import com.curso.mitiempo_jm.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val TAG = "MiTiempo"
    private val urlMunicipio =
        "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/"
    private lateinit var binding: ActivityMainBinding
    val apiKey = BuildConfig.AEMET_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        // setContentView(R.layout.activity_main)
     /*   ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }*/
        if (apiKey.isEmpty() /*|| apiKey.startsWith("eyJhbGci")*/) {
            Log.w("MainActivity", "La clave de AEMET no está configurada o es la de ejemplo.")
        } else {
            // Usar la clave para las llamadas a la API
            getDatosAemet("49166")
        }
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun getDatosAemet(codigoPoblacion: String) {
        showLoading()

        // `lifecycleScope` es la forma correcta y segura de lanzar corrutinas en una Activity.
        // Se cancela automáticamente si la pantalla se destruye, evitando errores y fugas de memoria.
        lifecycleScope.launch {
            try {
                // `withContext(Dispatchers.IO)` ejecuta la lógica de red en un hilo secundario
                // para no bloquear la interfaz de usuario. Al finalizar, devuelve el resultado.
                val prediccionFinal = withContext(Dispatchers.IO) {
                    Log.d(TAG, "Iniciando llamadas de red en hilo secundario...")

                    // --- PASO 1: Obtener la URL de los datos ---
                    val retrofitPaso1 = getRetrofit(urlMunicipio)
                    val servicePaso1 = retrofitPaso1.create(AemetApiService::class.java)
                    val responseUrl = servicePaso1.getUrlAemet(codigoPoblacion, apiKey =apiKey)


                    // Si la llamada falla o el estado no es 200, lanzamos un error que será
                    // capturado por el bloque 'catch'.
                    if (!responseUrl.isSuccessful || responseUrl.body()?.estado != 200) {
                        throw Exception("Error al obtener la URL de datos: ${responseUrl.body()?.descripcion}")
                    }

                    val urlDatosFinales = responseUrl.body()!!.datos
                    Log.d(TAG, "URL de datos obtenida: $urlDatosFinales")

                    // La API de AEMET requiere una pausa entre la primera y la segunda llamada.
                    delay(2000)

                    // --- PASO 2: Obtener la predicción del tiempo ---
                    val retrofitPaso2 = getRetrofit("https://opendata.aemet.es/")
                    val servicePaso2 = retrofitPaso2.create(AemetApiService::class.java)
                    val responsePrediccion = servicePaso2.getPrediccion(urlDatosFinales)

                    if (!responsePrediccion.isSuccessful) {
                        throw Exception("Error al obtener la predicción final.")
                    }

                    // Se devuelve el primer elemento de la respuesta, que será el resultado de `withContext`.
                    // Este será el objeto `PrediccionMunicipioResponseItem` que esperamos.
                    responsePrediccion.body()!!.first()
                }

                // Al salir de `withContext`, el código continúa en el hilo principal (Main).
                // Aquí podemos actualizar la UI de forma segura con el resultado obtenido.
                Log.d(TAG, "Llamadas de red completadas. Actualizando UI...")
                Log.d(
                    TAG,
                    "Temperatura máxima: ${prediccionFinal.prediccion.dia.first().temperatura.maxima}ªC"
                )
                Log.d(
                    TAG,
                    "Temperatura mínima: ${prediccionFinal.prediccion.dia.first().temperatura.minima}ªC"
                )
                Log.d(
                    TAG,
                    "Cota nieve provincia: respuesta.provincias.forEach { prov ->\n" +
                            prediccionFinal.prediccion.dia.first().cotaNieveProv.forEach { prov ->
                                "Log.d(TAG, \"Cota Nieve Provincia: ${prov.periodo} - ${prov.value}\"})
                            }

                )

                showWeatherData(prediccionFinal)

            } catch (e: Exception) {
                // El bloque `catch` maneja cualquier excepción lanzada en el `try`,
                // incluyendo errores de red o las excepciones que lanzamos manualmente.
                // Como este bloque se ejecuta en el hilo principal, podemos actualizar la UI.
                Log.e(TAG, "Excepción en getDatosAemet: ${e.message}", e)
                showError("Fallo al obtener los datos: ${e.message}")
            }
        }


    }
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.weatherDataContainer.visibility = View.GONE
        binding.errorTextView.visibility = View.GONE
    }
    private fun showWeatherData(prediccionMunicipio: MunicipioItem) {

        val hoy = prediccionMunicipio.prediccion.dia.first() // Tomamos el primer día de la lista
        Log.d(TAG, "Temperatura máxima hoy: ${hoy.temperatura.maxima}")
        val fechaElaboracion = prediccionMunicipio.elaborado
        val diaElaboracion = fechaElaboracion.split("T")[0].split("-")
        val hora = fechaElaboracion.split("T")[1]
        binding.labelTemp.text = "Temperatura en ${prediccionMunicipio.nombre}"
        binding.maxTempTextView.text = "Máx: ${hoy.temperatura.maxima}°C"
        binding.minTempTextView.text = "Mín: ${hoy.temperatura.minima}°C"
        binding.dateTextView.text="    Actualizado a las:\n$hora ${diaElaboracion[2]}/${diaElaboracion[1]}/${diaElaboracion[0]}"
        binding.progressBar.visibility = View.GONE
        binding.weatherDataContainer.visibility = View.VISIBLE
        binding.errorTextView.visibility = View.GONE


    }
    private fun showError(message: String) {
        binding.errorTextView.text = message
        binding.progressBar.visibility = View.GONE
        binding.weatherDataContainer.visibility = View.GONE
        binding.errorTextView.visibility = View.VISIBLE
    }
}