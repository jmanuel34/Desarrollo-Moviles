package com.curso.mitiempo_jm.data_network

data class MunicipioItem(
    val elaborado: String,
    val id: Int,
    val nombre: String,
    val origen: Origen,
    val prediccion: Prediccion,
    val provincia: String,
    val version: Double
)