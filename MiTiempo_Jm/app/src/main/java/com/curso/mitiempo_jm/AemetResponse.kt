package com.curso.mitiempo_jm

import com.google.gson.annotations.SerializedName

/**
 * Nota: Como el JSON raíz es un Array [ ... ], en tu llamada de Retrofit
 * o parseador debes esperar un List<AemetResponse>
 */
data class AemetResponse(
    @SerializedName("origen") val origen: Origen,
    @SerializedName("elaborado") val elaborado: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("provincia") val provincia: String,
    @SerializedName("prediccion") val prediccion: Prediccion,
    @SerializedName("id") val id: Int,
    @SerializedName("version") val version: Double
)

data class Origen(
    @SerializedName("productor") val productor: String,
    @SerializedName("web") val web: String,
    @SerializedName("enlace") val enlace: String,
    @SerializedName("language") val language: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("notaLegal") val notaLegal: String
)

data class Prediccion(
    @SerializedName("dia") val dia: List<Dia>
)

data class Dia(
    @SerializedName("fecha") val fecha: String,
    @SerializedName("probPrecipitacion") val probPrecipitacion: List<DatoPrecipitacion>,
    @SerializedName("cotaNieveProv") val cotaNieveProv: List<DatoCotaNieve>,
    @SerializedName("estadoCielo") val estadoCielo: List<EstadoCielo>,
    @SerializedName("viento") val viento: List<Viento>,
    @SerializedName("rachaMax") val rachaMax: List<DatoRacha>,
    @SerializedName("temperatura") val temperatura: InfoTemperatura,
    @SerializedName("sensTermica") val sensTermica: InfoTemperatura,
    @SerializedName("humedadRelativa") val humedadRelativa: InfoTemperatura,
    // uvMax desaparece en los últimos registros del JSON, por eso es Int? (nullable)
    @SerializedName("uvMax") val uvMax: Int? = null
)

// Clase para probabilidad de precipitación (value es Int en el JSON: 0, 100, 15)
data class DatoPrecipitacion(
    @SerializedName("value") val value: Int,
    @SerializedName("periodo") val periodo: String? = null
)

// Clase para cota de nieve (value es String en el JSON: "2000", "")
data class DatoCotaNieve(
    @SerializedName("value") val value: String,
    @SerializedName("periodo") val periodo: String? = null
)

// Clase para racha máxima (value es String en el JSON)
data class DatoRacha(
    @SerializedName("value") val value: String,
    @SerializedName("periodo") val periodo: String? = null
)

data class EstadoCielo(
    @SerializedName("value") val value: String,
    @SerializedName("periodo") val periodo: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null
)

data class Viento(
    @SerializedName("direccion") val direccion: String? = null,
    @SerializedName("velocidad") val velocidad: Int,
    @SerializedName("periodo") val periodo: String? = null
)

// Reutilizable para temperatura, sensación térmica y humedad relativa
data class InfoTemperatura(
    @SerializedName("maxima") val maxima: Int,
    @SerializedName("minima") val minima: Int,
    @SerializedName("dato") val dato: List<DatoHorario>
)
data class DatoHorario(
    @SerializedName("value") val value: Int,
    @SerializedName("hora") val hora: Int? = null
)