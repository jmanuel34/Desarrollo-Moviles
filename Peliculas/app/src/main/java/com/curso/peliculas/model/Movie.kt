package com.curso.peliculas.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Runtime") val duration: String?,
    @SerializedName("Director") val director: String?,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("imdbRating") val rating: String?,
    @SerializedName("Poster") val poster: String,
    @SerializedName("imdbID") val imdbID: String
) : Serializable