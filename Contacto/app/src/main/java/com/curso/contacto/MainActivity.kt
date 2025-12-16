package com.curso.contacto

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.curso.contacto.databinding.ActivityMainBinding
import com.curso.contacto.db.ContactoDatabase
import com.curso.contacto.db.dao.ContactoDao
import com.curso.contacto.db.entity.Contacto
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

     */
    private lateinit var binding: ActivityMainBinding
    private val db by lazy { ContactoDatabase.getDatabase(this) }
    private val contactoDao by lazy { db.contactoDao() }

    //https://github.com/serpro69/kotlin-faker
    private val faker = Faker()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            //userDao.insert(user)
            //*
            generateFakeUsers(20).forEach {
                ContactoDao.insert(it)
            }
            //*/
        }

    }
    /**
     * Genera una lista de usuarios falsos utilizando la librería Faker.
     * @param count El número de usuarios a crear.
     * @return Una lista de objetos [User].
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateFakeUsers(count: Int): List<Contacto> {
        val userList = mutableListOf<Contacto>()
        repeat(count) {

            val user = Contacto(
                nombre = faker.name.firstName(),
                apellidos = faker.name.lastName(),
                telefonoMovil = Random.nextInt(16, 80),
                email = faker.internet.email(), // Genera un email,
                cumpleanos = faker.date.birthday().toString(),
                rutaFotoPerfil = null // Dejamos la imagen como nula por ahora
            )
            userList.add(user)
        }
        return userList
    }
    /**
     * Genera una fecha de cumpleaños aleatoria y la devuelve como String.
     * @param minAge La edad mínima que puede tener la persona (ej: 18)
     * @param maxAge La edad máxima que puede tener la persona (ej: 80)
     * @param format El patrón de formato deseado para el String (ej: "dd/MM/yyyy")
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateRandomBirthdayString(
        minAge: Long = 18,
        maxAge: Long = 80,
        format: String = "yyyy-MM-dd"
    ): String {
        val range = maxAge - minAge + 1

        val randomDay = kotlin.random.Random.nextLong(range) + 1
        // 6. Formatear la fecha a String
        val formatter = DateTimeFormatter.ofPattern(format)
        return randomDay.fo
    }


}