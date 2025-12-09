
package com.example.poo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Configuración para el modo edge-to-edge (pantalla completa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Inicializar y configurar los 6 botones ---

        // Buscamos cada botón en el layout por su ID
        val boton1: Button = findViewById(R.id.boton_1)
        val boton2: Button = findViewById(R.id.boton_2)
        val boton3: Button = findViewById(R.id.boton_3)
        val boton4: Button = findViewById(R.id.boton_4)
        val boton5: Button = findViewById(R.id.boton_5)
        val boton6: Button = findViewById(R.id.boton_6)

        // Asignamos una acción a cada botón cuando se hace clic
        boton1.setOnClickListener {
            val mensaje = "Has pulsado el Botón 1"
            // Muestra un mensaje en el Logcat (para depuración)
            Log.d("MainActivity", mensaje)
            // Muestra un mensaje temporal en la pantalla (para el usuario)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }

        boton2.setOnClickListener {
            val mensaje = "Has pulsado el Botón 2"
            Log.d("MainActivity", mensaje)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }

        boton3.setOnClickListener {
            val mensaje = "Has pulsado el Botón 3"
            Log.d("MainActivity", mensaje)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }

        boton4.setOnClickListener {
            val mensaje = "Has pulsado el Botón 4"
            Log.d("MainActivity", mensaje)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }

        boton5.setOnClickListener {
            val mensaje = "Has pulsado el Botón 5"
            Log.d("MainActivity", mensaje)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }

        boton6.setOnClickListener {
            val mensaje = "Has pulsado el Botón 6"
            Log.d("MainActivity", mensaje)
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}
