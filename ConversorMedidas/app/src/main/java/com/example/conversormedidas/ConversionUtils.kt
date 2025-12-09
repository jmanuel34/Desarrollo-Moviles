package com.example.conversormedidas
object ConversionUtils {
    /**
     * 1. El mapa es ahora la única fuente de verdad para las
    conversiones.
     * Asocia un par de unidades (origen, destino) con su función de
    conversión.
     * La clave es un `Pair<String, String>` (ej: "Metros" to "Pies").
     * El valor es una referencia a la función que realiza el cálculo
    (ej: `::metersToFeet`).
     * Este diseño permite añadir nuevas conversiones fácilmente con
    solo añadir una entrada al mapa.
     */
    private val conversions: Map<Pair<String, String>, (Double) -> Double>
            = mapOf(
// Longitud
// Define una conversión: la clave es el par de unidades ("Metros a "Pies"),
// y el valor es la referencia a la función que realiza la conversión.
    ("Metros" to "Pies") to ::metersToFeet,
    ("Pies" to "Metros") to ::feetToMeters,
    ("Kilómetros" to "Millas") to ::kilometersToMiles,
    ("Millas" to "Kilómetros") to ::milesToKilometers,
    ("Centímetros" to "Pulgadas") to ::centimetersToInches,
    ("Pulgadas" to "Centímetros") to ::inchesToCentimeters,
    ("Metros" to "Yardas") to ::metersToYards,
    ("Yardas" to "Metros") to ::yardsToMeters,
    ("Kilómetros" to "Yardas") to ::kilometersToYards,
    ("Yardas" to "Kilómetros") to ::yardsToKilometers,
    ("Milímetros" to "Pulgadas") to ::millimetersToInches,
    ("Pulgadas" to "Milímetros") to ::inchesToMillimeters,
// Peso
    ("Kilogramos" to "Libras") to ::kilogramsToPounds,
    ("Libras" to "Kilogramos") to ::poundsToKilograms,
    ("Gramos" to "Onzas") to ::gramsToOunces,
    ("Onzas" to "Gramos") to ::ouncesToGrams,
    ("Toneladas Métricas" to "Toneladas USA") to ::metricTonsToUSTons,
    ("Toneladas USA" to "Toneladas Métricas") to ::usTonsToMetricTons,
// Volumen
    ("Litros" to "Galones (USA)") to ::litersToGallonsUS,
    ("Galones (USA)" to "Litros") to ::gallonsUSToLiters,
    ("Mililitros" to "Onzas Líquidas (USA)") to
    ::millilitersToFluidOuncesUS,
    ("Onzas Líquidas (USA)" to "Mililitros") to
    ::fluidOuncesUSToMilliliters,
    ("Litros" to "Pintas (USA)") to ::litersToPintsUS,
    ("Pintas (USA)" to "Litros") to ::pintsUSToLiters,
    ("Litros" to "Cuartos (USA)") to ::litersToQuartsUS,
    ("Cuartos (USA)" to "Litros") to ::quartsUSToLiters,
// Temperatura
    ("Celsius" to "Fahrenheit") to ::celsiusToFahrenheit,
    ("Fahrenheit" to "Celsius") to ::fahrenheitToCelsius,
    ("Celsius" to "Kelvin") to ::celsiusToKelvin,
    ("Kelvin" to "Celsius") to ::kelvinToCelsius,
    ("Fahrenheit" to "Kelvin") to ::fahrenheitToKelvin,
    ("Kelvin" to "Fahrenheit") to ::kelvinToFahrenheit
    )
    /**
     * 2. Devuelve una lista de todas las unidades únicas y ordenadas,
     * generada dinámicamente desde el mapa de conversiones.
     */
    fun getAvailableUnits(): List<String> {
        return conversions.keys.flatMap { listOf(it.first, it.second)
        }.distinct().sorted()
    }
    /**
     * 3. Un único punto de entrada para convertir un valor de una unidad
    a otra.
     * @return El valor convertido como un `Double`, o `null` si la
    conversión no es soportada.
     */
    fun convert(value: Double, from: String, to: String): Double? {
        if (from == to) return value
        return conversions[from to to]?.invoke(value)
    }

    // --- 4. Las funciones de conversión ahora son privadas ---
    private fun metersToFeet(m: Double) = m * 3.28084
    private fun feetToMeters(ft: Double) = ft / 3.28084
    fun kilometersToMiles(km: Double) = km * 0.621371
    fun milesToKilometers(mi: Double) = mi / 0.621371
    fun centimetersToInches(cm: Double) = cm / 2.54
    fun inchesToCentimeters(inch: Double) = inch * 2.54
    fun metersToYards(m: Double) = m * 1.09361
    fun yardsToMeters(yd: Double) = yd / 1.09361
    fun kilometersToYards(km: Double) = km * 1093.61
    fun yardsToKilometers(yd: Double) = yd / 1093.61
    fun millimetersToInches(mm: Double) = mm / 25.4
    fun inchesToMillimeters(inch: Double) = inch * 25.4
    // ⚖Peso
    fun kilogramsToPounds(kg: Double) = kg * 2.20462
    fun poundsToKilograms(lb: Double) = lb / 2.20462
    fun gramsToOunces(g: Double) = g / 28.3495
    fun ouncesToGrams(oz: Double) = oz * 28.3495
    fun metricTonsToUSTons(t: Double) = t * 1.10231
    fun usTonsToMetricTons(usTon: Double) = usTon / 1.10231
    // 💧 Volumen
    fun litersToGallonsUS(L: Double) = L / 3.78541
    fun gallonsUSToLiters(gal: Double) = gal * 3.78541

    fun millilitersToFluidOuncesUS(ml: Double) = ml / 29.5735
    fun fluidOuncesUSToMilliliters(flOz: Double) = flOz * 29.5735
    fun litersToPintsUS(L: Double) = L * 2.11338
    fun pintsUSToLiters(pt: Double) = pt / 2.11338
    fun litersToQuartsUS(L: Double) = L * 1.05669
    fun quartsUSToLiters(qt: Double) = qt / 1.05669
    // 🌡 Temperatura
    fun celsiusToFahrenheit(C: Double) = (C * 9/5) + 32
    fun fahrenheitToCelsius(F: Double) = (F - 32) * 5/9
    fun celsiusToKelvin(C: Double) = C + 273.15
    fun kelvinToCelsius(K: Double) = K - 273.15
    fun fahrenheitToKelvin(F: Double) = (F - 32) * 5/9 + 273.15
    fun kelvinToFahrenheit(K: Double) = (K - 273.15) * 9/5 + 32
}