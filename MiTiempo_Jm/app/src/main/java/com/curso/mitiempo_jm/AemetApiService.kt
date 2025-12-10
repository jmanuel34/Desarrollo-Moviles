package com.curso.mitiempo_jm

import retrofit2.Response
import retrofit2.http.GET

interface AemetApiService {
    @GET("prediccion/especifica/municipio/diaria/08025") // O la URL completa
    suspend fun obtenerPrediccion(): Response<List<AemetResponse>>
}