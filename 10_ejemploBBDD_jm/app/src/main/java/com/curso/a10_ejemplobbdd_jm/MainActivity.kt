package com.curso.a10_ejemplobbdd_jm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.curso.a10_ejemplobbdd_jm.db.dao.AppDatabase
import com.curso.a10_ejemplobbdd_jm.db.dao.User
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Obtenemos una instancia de la base de datos
        val db = AppDatabase.getDatabase(this)

        // 2. Usamos un lifecycleScope para lanzar una corrutina
        lifecycleScope.launch {
            // 3. Creamos un nuevo usuario
            val newUser =
                User(firstName = "Madrina", lastName = "Lovelace", age = 36, email = "ada@lovelace.com")

            // 4. Usamos el DAO para insertarlo en un hilo de fondo
            db.userDao().insert(newUser)
        }
    }
}