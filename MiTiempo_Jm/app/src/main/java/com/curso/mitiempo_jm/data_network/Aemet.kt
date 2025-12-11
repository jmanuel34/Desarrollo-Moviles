package com.curso.mitiempo_jm.data_network

data class Aemet(
    val datos: String,
    val descripcion: String,
    val estado: Int,
    val metadatos: String
)