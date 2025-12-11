package com.curso.mitiempo_jm.data_network

data class HumedadRelativa(
    val dato: List<Dato>,
    val maxima: Int,
    val minima: Int
)