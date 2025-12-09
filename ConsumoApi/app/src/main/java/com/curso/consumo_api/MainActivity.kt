package com.curso.consumo_api

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
            private fun getRetrofit(): Retrofit {
                return Retrofit.Builder()
                    .baseUrl("https://imdb.iamidiotareyoutoo.com") // URL Base para la búsqueda
                    .addConverterFactory(GsonConverterFactory.create()) // Conversor de JSON
                    .build()
            }
        }
    }
}