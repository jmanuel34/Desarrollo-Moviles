package com.curso.consumo_api

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "ConsumoAPIs"
    override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top,
                systemBars.right, systemBars.bottom)
            insets
        }
// Iniciamos la búsqueda al crear la Activity
        searchMovie("batman")
//        Log.d(TAG, "onCreate").toString()
    }

    /**
     * Configura y crea una instancia de Retrofit. Es una buena práctica
     * centralizar esta configuración en una sola función.
     */
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://imdb.iamidiotareyoutoo.com") // URL Base para la búsqueda
            .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON
            .build()
    }
    /**
     * Realiza la llamada a la API en un hilo secundario usando corutinas.
     */
    private fun searchMovie(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                getRetrofit().create(ApiService::class.java).findMovieByTitle(query)
            val movie = call.body()

            val primerElemento =movie?.description[0]
            runOnUiThread {
                if (call.isSuccessful) {
                    movie.description.forEach {  }()){
                    forEach { pelicula ->
                        Log.d(TAG, "Película: ${pelicula.title} | Año: ${pelicula.year} | ID: ${pelicula.imdbId}")
                    }
      //              Log.d(TAG, "Respuesta del servidor:${primerElemento.toString()}}")
      //              Log.d(TAG, "Respuesta Peliculas:${movie?.toString()}}")
                } else {
                    Log.e(TAG, "Error en la llamada:${call.errorBody()?.string()}")
                }
            }
        }
    }
}

/*
//*/
private fun searchMovie(query: String) {
    CoroutineScope(Dispatchers.IO).launch {

        // 1. Ejecutar la llamada de manera síncrona en el hilo de IO
        // NOTA: Asumo que getRetrofit().create(...).findMovieByTitle(query) devuelve Call<List<MovieResponse>>
        val call = getRetrofit().create(ApiService::class.java).findMovieByTitle(query)

        // El objeto 'movieList' es el cuerpo de la respuesta, que debería ser List<MovieResponse>?
        // Si tu API devuelve una lista directamente:
        val movieList: List<MovieResponse>? = call.body()

        // El resto del código que interactúa con la UI debe ir en el hilo principal
        runOnUiThread {
            if (call.isSuccessful) {

                if (!movieList.isNullOrEmpty()) {

                    Log.d(TAG, "--- Inicia Listado de Películas ---")

                    // 2. ⭐ CLAVE: Iterar sobre la lista y usar Log.d() para cada elemento
                    movieList.forEach { pelicula ->
                        Log.d(TAG, "Película: ${pelicula.title} | Año: ${pelicula.year} | ID: ${pelicula.imdbId}")
                    }

                    Log.d(TAG, "--- Fin del Listado (Total: ${movieList.size}) ---")

                } else {
                    // Manejar el caso de lista vacía
                    Log.w(TAG, "La búsqueda no arrojó resultados válidos.")
                }
            } else {
                // Manejar errores HTTP (ej: 404, 500)
                Log.e(TAG, "Error en la llamada: Código ${call.code()}. Mensaje: ${call.errorBody()?.string()}")
            }
        }
    }
 */
 */