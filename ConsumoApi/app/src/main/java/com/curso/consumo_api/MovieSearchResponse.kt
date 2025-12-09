package com.curso.consumo_api

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    val ok: Boolean,
    @SerializedName("error_code") val errorCode: Int,
    val description: List<MovieResponse>,
)

data class MovieResponse(
    /*
    "#TITLE": "Batman",
    "#YEAR": 1966,
    "#IMDB_ID": "tt0059968",
    "#RANK": 4416,
    "#ACTORS": "Adam West, Burt Ward",
    "#AKA": "Batman (1966) ",
    "#IMDB_URL": "https://imdb.com/title/tt0059968",
    "#IMDB_IV": "https://IMDb.iamidiotareyoutoo.com/title/tt0059968",

    "#IMG_POSTER": "https://m.media-
    amazon.com/images/M/MV5BZWFjNTdlZjctZTRkNC00OTQ1LWI3NDktOWY0ZWZmNzFiOTNkXk

    EyXkFqcGc@._V1_.jpg",
    "photo_width": 1084,
    "photo_height": 1645
    */
// La anotación le dice a Gson que la clave "#TITLE" en el JSON
// corresponde a la propiedad "title" en esta clase.
    @SerializedName("#TITLE") val title: String,
    @SerializedName("#YEAR") val year: Int,
    @SerializedName("#IMDB_ID") val imdbId: String,
    @SerializedName("#RANK") val rank: Int,
    @SerializedName("#ACTORS") val actors: String,
    @SerializedName("#AKA") val aka: String,
    @SerializedName("#IMDB_URL") val imdbUrl: String,
    @SerializedName("#IMDB_IV") val imdbIv: String,
    @SerializedName("#IMG_POSTER") val posterUrl: String,
    @SerializedName("photo_width") val photoWidth: Int,
    @SerializedName("photo_height") val photoHeight: Int,
)