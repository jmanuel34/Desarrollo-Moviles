package com.example.mysegundoejemplo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Demostración de que se puede crear una Tecla sin una instancia de Teclado.
        // Esto es posible porque Tecla es una clase anidada (nested class), no una clase interna (inner class).
        val teclaEnter = Teclado.Tecla(13)
        println("Se ha creado una tecla con el código: ${teclaEnter.codigo}") // Imprimirá en la consola Logcat de Android Studio
    }
}

/**
 * Clase que representa un Teclado.
 */
class Teclado {
    // Esta es una clase anidada (nested class).
    // No tiene acceso a los miembros de la instancia de la clase externa (Teclado).
    class Tecla(val codigo: Int) {
        fun mostrarCodigo() {
            println("El código de la tecla es: $codigo")
        }
    }
}
