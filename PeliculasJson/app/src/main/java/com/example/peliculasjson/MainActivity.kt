package com.example.peliculasjson

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView


/**
 * Actividad principal del ejemplo.
 *
 * Muestra la lista de películas en un `RecyclerView` y proporciona una barra de
 * búsqueda (`SearchView`) para filtrar resultados por título.
 *
 * Flujo:
 * 1. Carga `movies.json` desde `assets` usando `readAssetFile`.
 * 2. Parsea el JSON con `parseWithGson` y muestra la lista en el adapter.
 * 3. Escucha cambios en `SearchView` y filtra la lista en tiempo real.
 */
class MainActivity : AppCompatActivity() {






    private val TAG = "MainActivityJSON"
    private var moviesList: List<MovieGson> = emptyList()

    /**
     * Inicializa la UI: RecyclerView + SearchView, carga datos y establece listeners.
     *
     * @param savedInstanceState Estado previo de la actividad (si existe).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // opcional: habilitar estilo edge-to-edge
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.textView)




        // Leer JSON desde assets
        val jsonText = readAssetFile(this, "movies.json")
        if (jsonText == null) {
            Log.e(TAG, "No se pudo leer el asset movies.json")
            return
        }
        // Log.i(  TAG, "Contenido : $jsonText")

        //*

        // Parsear con Gson y popular la lista
        moviesList = parseWithGson(jsonText)
        Log.i(TAG, "Gson parsed ${moviesList.size} movies ")
        Log.i(TAG, "Gson parsed ${moviesList[0]} movies ")
        // También parsear con kotlinx.serialization solo para log (sin usar en la UI)
        val moviesKs = parseWithKotlinx(jsonText)
        Log.i(TAG, "kotlinx.serialization parsed ${moviesKs.size} movies")


        tv.text = "Cantidad de peliculas:\n ${countMoviesFromAsset(moviesList)}"
       // tv.text = "Probando"
        //*/
        /**
         * Contar peliculas:
         * Input:
         * Output: Integer
         */
       Log.i(TAG, "Cantidad de peliculas desde el Main: ${countMoviesFromAsset(moviesList)}")

        Log.i( TAG, "${filterByYearAndGenre(moviesList, 1980, "Drama")}")

       //*
        //

        val minYear = 2000
        val targetGenre = "Drama"
        val filteredMovies = filterByYearAndGenre(
            movies = moviesList,
            yearMin = minYear,
            genre = targetGenre
        )
        if (filteredMovies.isEmpty()) {
            Log.d(TAG, "No se encontraron películas que cumplan con los criterios.")
        } else {
            filteredMovies.forEach { movie ->
                // Imprimimos el título, año y géneros de cada película encontrada
                Log.d(TAG, "Título: ${movie.title}, Año: ${movie.year}, Géneros: ${movie.genres.joinToString()}")

            }
            Log.d(TAG,"Numero de peliculas: " + filteredMovies.size)
        }
        }
        //*/
    }