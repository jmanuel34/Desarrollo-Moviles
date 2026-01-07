package com.curso.peliculas.network;

import com.curso.peliculas.model.Movie
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    // Buscar una lista de películas
    @GET("/")
    fun searchMovies(
            @Query("s") query: String,
            @Query("apikey") apiKey: String
    ): Call<MovieSearchResponse>

    // Obtener detalles de una película específica por su ID
    @GET("/")
    fun getMovieDetails(
            @Query("i") imdbId: String,
            @Query("apikey") apiKey: String
    ): Call<Movie>
}

data class MovieSearchResponse(
    @SerializedName("Search") val search: List<Movie>?,
    @SerializedName("Response") val response: String
)
