package com.curso.a10_ejemplobbdd_jm


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.curso.a10_ejemplobbdd_jm.db.ContactoDatabase
import com.curso.a10_ejemplobbdd_jm.db.entity.User
import com.curso.a10_ejemplobbdd_jm.R
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Test
        // 1. Obtenemos una instancia de la base de datos
        val db = ContactoDatabase.getDatabase(this)

        // 2. Usamos un lifecycleScope para lanzar una corrutina
        lifecycleScope.launch {
            // 3. Creamos un nuevo usuario
            val newUser =
                User(firstName = "Padrina", lastName = "Lovelace", age = 36, email = "ada@lovelace.com")

            // 4. Usamos el DAO para insertarlo en un hilo de fondo
            db.userDao().insert(newUser)
        }
    }
}