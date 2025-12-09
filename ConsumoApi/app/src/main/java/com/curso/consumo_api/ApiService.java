package com.curso.consumo_api;

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface APIService {
// Definimos una petición GET al endpoint "/search".
// Este se combinará con la URL base:
    https://search.imdbot.workers.dev/search
    @GET("/search")
    // Usamos @Query para añadir parámetros a la URL (ej: ?q=batman)
// La función es 'suspend' para poder ser llamada desde una corutina.
    suspend fun findMovieByTitle(@Query("q") title: String):
    Response<MovieSearchResponse>
}