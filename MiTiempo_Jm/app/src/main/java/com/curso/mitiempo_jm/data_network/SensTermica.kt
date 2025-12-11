package com.curso.mitiempo_jm.data_network

data class SensTermica(
    val dato: List<Dato>,
    val maxima: Int,
    val minima: Int
)