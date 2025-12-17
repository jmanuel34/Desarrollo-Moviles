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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
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
            //*
            val user = Contacto(
                nombre = faker.name.firstName(),
                apellidos = faker.name.lastName(),
                telefonoFijo = null,
                telefonoMovil = Random.nextInt(16, 80).toString(),
                direccion = faker.address.toString(),
                empresa = faker.industrySegments.toString(),
                email = faker.internet.email(),
                cumpleanos = generarFechaFicticiaComoString(),
                fotoPerfilUri= null
            )
            userList.add(user)
        }
        return userList
    }


    fun generarFechaFicticiaComoString(formato: String = "dd-MM-yyyy"): String {
        val calendar = Calendar.getInstance()
        val random = java.util.Random()

        // Definir rango de años (ajusta según necesites)
        val minYear = 1970
        val maxYear = 2025
        val randomYear = random.nextInt(maxYear - minYear + 1) + minYear

        // Día del año: de 1 a 365 (o 366, pero simplificamos)
        val randomDayOfYear = random.nextInt(365) + 1

        calendar.set(Calendar.YEAR, randomYear)
        calendar.set(Calendar.DAY_OF_YEAR, randomDayOfYear)

        val sdf = SimpleDateFormat(formato, Locale.getDefault())
        return sdf.format(calendar.time)
    }
    /*
    fun generarContacto() {
        val nuevoNombre = "Juan"
        val nuevosApellidos = "Pérez García"
        val nuevoMovil = "600123456"
        val nuevoFijo: String? = "910987654" // Opcional, puede ser null
        val nuevaDireccion: String? = null // Opcional, en este caso lo dejamos null

        val miNuevoContacto = Contacto(

            nombre = faker.name.firstName(),
            apellidos = faker.name.lastName(),
            telefonoFijo = null,
            telefonoMovil = Random.nextInt(616111111, 699999999).toString(),
            direccion = nuevaDireccion,
            empresa = "Mi Empresa S.L.",
            email = "juan.perez@ejemplo.com",
            cumpleanos = "1990-05-15",
            //  notas = "Amigo de la universidad"
            // Sin foto por ahora
            // id = 0L // No es necesario si usas el valor por defecto
        )
    }

     */

}