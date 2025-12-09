package com.example.peliculasjson

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import kotlin.math.sign

/**
 * Tag para los logs emitidos por las utilidades de este archivo.
 */
private const val TAG = "JsonParsers"

/**
 * JsonParsers.kt
 * ----------------
 * Utilidades para leer archivos JSON desde la carpeta `assets` y parsearlos
 * usando dos bibliotecas diferentes: Gson y kotlinx.serialization.
 *
 * Contiene:
 * - readAssetFile: lee un archivo dentro de assets y devuelve su contenido.
 * - parseWithGson: parsea JSON a modelos Kotlin usando Gson.
 * - parseWithKotlinx: parsea JSON a modelos Kotlin usando kotlinx.serialization.
 * - Modelos de datos para ambas bibliotecas.
 */

/**
 * Lee un archivo desde `assets` y devuelve su contenido como String.
 *
 * @param context Context necesario para acceder a `assets`.
 * @param fileName Nombre del archivo dentro de `assets` (por ejemplo "movies.json").
 * @return El contenido del archivo como String, o null si ocurre un error.
 */
fun readAssetFile(context: Context, fileName: String): String? {
    return try {
        // Abrimos el asset y leemos todo el texto de forma segura (usa bufferedReader y use).
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        // Logueamos el error y devolvemos null para que el llamador gestione el fallo.
        Log.e(TAG, "Error leyendo asset: $fileName", e)
        null
    }
}

// --- Gson models & parsing ---

/**
 * Modelo de datos simple usado por Gson.
 * Sus propiedades deben coincidir con los nombres de las claves del JSON.
 */
data class MovieGson(
    val title: String,
    val year: Int,
    val genres: List<String>,
    val rating: Double,
    val cast: List<String>
)

/**
 * Contenedor que representa la estructura raíz del JSON de ejemplo:
 * { "movies": [ ... ] }
 *
 * @SerializedName se usa para mapear la clave "movies" al campo `movies`.
 */
data class MoviesContainerGson(
    @SerializedName("movies") val movies: List<MovieGson>
)

/**
 * Parsear JSON usando Gson.
 *
 * @param jsonText Texto JSON a parsear.
 * @return Lista de MovieGson; vacía si ocurre un error.
 */
fun parseWithGson(jsonText: String): List<MovieGson> {
    val gson = Gson()
    return try {
        // Deserializamos al contenedor raíz y devolvemos la lista de películas.
        val container = gson.fromJson(jsonText, MoviesContainerGson::class.java)
        container.movies
    } catch (e: Exception) {
        // Si falla el parseado, lo logueamos y retornamos una lista vacía.
        Log.e(TAG, "Error parseando JSON con Gson", e)
        emptyList()
    }
}
    fun countMoviesFromAsset(listMovies : List<MovieGson> ) : Int {

        Log.i(TAG, "Cantidad de peliculas: ${listMovies.size}")
            return listMovies.size
        }


// --- kotlinx.serialization models & parsing ---

/**
 * Modelo de datos para kotlinx.serialization.
 * La anotación @Serializable permite que la librería genere el código necesario.
 */
@Serializable
data class MovieKs(
    val title: String,
    val year: Int,
    val genres: List<String>,
    val rating: Double,
    val cast: List<String>
)

/**
 * Contenedor raíz para kotlinx.serialization.
 */
@Serializable
data class MoviesContainerKs(val movies: List<MovieKs>)

/**
 * Parsear JSON usando kotlinx.serialization.
 * Se configura el parser con `ignoreUnknownKeys` para tolerar claves extra.
 *
 * @param jsonText Texto JSON a parsear.
 * @return Lista de MovieKs; vacía si ocurre un error.
 */
fun parseWithKotlinx(jsonText: String): List<MovieKs> {
    return try {
        val json = Json { ignoreUnknownKeys = true }
        // decodeFromString convierte el String JSON al tipo indicado.
        val container = json.decodeFromString<MoviesContainerKs>(jsonText)
        container.movies
    } catch (e: Exception) {
        Log.e(TAG, "Error parseando JSON con kotlinx.serialization", e)
        emptyList()
    }
}
fun filterByYearAndGenre(movies: List<MovieGson>, yearMin: Int,  genre: String) : List<MovieGson> {
    return movies.filter { movie ->
        movie.year >= yearMin && genre in movie.genres
    }
}
