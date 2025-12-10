package com.curso.mitiempo_jm

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val apiKey = BuildConfig.AEMET_API_KEY
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        if (apiKey.isEmpty() /*|| apiKey.startsWith("eyJhbGci")*/) {
            Log.w("MainActivity", "La clave de AEMET no está configurada o es la de ejemplo.")
        } else {
            // Usar la clave para las llamadas a la API
            Log.d("MainActivity", "API Key: $apiKey")
        }
    }
}