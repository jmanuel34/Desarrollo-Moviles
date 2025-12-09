package com.example.conversormedidas
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputValue = findViewById<EditText>(R.id.inputValue)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val txtResult = findViewById<TextView>(R.id.txtResult)
// Opciones de unidades
        val units = ConversionUtils.getAvailableUnits().toTypedArray()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, units
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
        btnConvert.setOnClickListener { view -> //'view es la View que se pulsó
            val value = inputValue.text.toString().toDoubleOrNull()
            if (value != null) {
                val fromUnit = spinnerFrom.selectedItem.toString()
                val toUnit = spinnerTo.selectedItem.toString()
                val result = ConversionUtils.convert(value, fromUnit, toUnit)
                txtResult.text = "$value $fromUnit = $result $toUnit"
                // 3. Comprobamos si la conversión fue exitosa.
                if (result != null) {
                    txtResult.text = "$value $fromUnit = $result $toUnit"
                } else {
                    Snackbar.make(
                        view,
                        "La conversión de $fromUnit a$toUnit no está soportada",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
// Si quitamos view -> hay que realizarlo con it
// Snackbar.make(it, "Introduce un número válido", Snackbar.LENGTH_SHORT).show()
// it es una forma concisa y práctica que ofrece Kotlin para referirse al único argumento de una lambda,
// evitando la necesidad de nombrarlo explícitamente.
            } else {
                Snackbar.make(
                    view, "Introduce un número válido",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
