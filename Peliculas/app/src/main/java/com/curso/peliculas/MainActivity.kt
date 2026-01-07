package com.curso.peliculas

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.curso.peliculas.network.MovieSearchResponse
import com.curso.peliculas.network.OmdbApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OmdbApiService::class.java)
        val apiKey = BuildConfig.OMDB_API_KEY

        service.searchMovies("abc", apiKey).enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
                val peliculas = response.body()?.search
                /* Esta es la construccion correcta de la llamada a la api rest. Añadir la ApiKey
                http://www.omdbapi.com/?s=abc&y=2024&apikey=
                */
                // Mostramos la primera película encontrada en el Log
                if (!peliculas.isNullOrEmpty()) {
                    val primeraPeli = peliculas[0]
                    Log.d("API_TEST", "Película encontrada: ${primeraPeli.title} (${primeraPeli.year})")
                } else {
                    Log.d("API_TEST", "No se encontraron películas o la respuesta fue vacía")
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Log.e("API_TEST", "Error en la llamada: ${t.message}")
            }
        })
    }
}
