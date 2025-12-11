package com.curso.mitiempo_jm

import com.curso.mitiempo_jm.data_network.Aemet
import com.curso.mitiempo_jm.data_network.Municipio
import com.curso.mitiempo_jm.data_network.MunicipioItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

interface AemetApiService {
    @GET("/opendata/api/prediccion/especifica/municipio/diaria/{codigoMunicipio}")
    suspend fun getUrlAemet(
        @Path("codigoMunicipio") codigoMuni: String, // Este parámetro reemplazará a {codigoMunicipio} en la URL
        @Header("api_key") apiKey: String             // Es mejor práctica pasar la API Key como un Header
    ): Response<Aemet>

    /**
     * Realiza la segunda llamada a AEMET para obtener la predicción meteorológica final.
     * Esta función ignora la URL base de Retrofit y usa la que se le pasa por parámetro.
     * @param url La URL completa obtenida en la primera llamada (del campo 'datos').
     * @return Una respuesta que contiene una lista de [com.curso.mitiempo.PrediccionResponse].
     */
    @GET
    suspend fun getPrediccion(
        @Url url: String
    ): Response<Municipio> // AEMET devuelve un Array de objetos, por eso es una List
}