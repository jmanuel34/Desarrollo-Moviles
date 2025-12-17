package com.curso.contacto

import ContactAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.curso.contacto.databinding.ActivityMainBinding
import com.curso.contacto.db.ContactoDatabase
import com.curso.contacto.db.dao.ContactoDao
import com.curso.contacto.db.entity.Contacto
import com.curso.contacto.ui.DetailActivity
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db by lazy { ContactoDatabase.getDatabase(this) }
    private val contactoDao by lazy { db.contactoDao() }
    //https://github.com/serpro69/kotlin-faker
    private val faker = Faker()

    @RequiresApi(Build.VERSION_CODES.O)
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // Dentro de onCreate de MainActivity
        binding.fabAddContact.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
        lifecycleScope.launch {

           generateFakeUsers(50).forEach {
                contactoDao.insert(it)
            }
            //*/

            val listaContactos = contactoDao.getAll().firstOrNull() ?: emptyList()
            Log.i("Datos", listaContactos.toString())
            val adapter = ContactAdapter(listaContactos)
            binding.recyclerViewContacts.adapter = adapter
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
                telefonoMovil = Random.nextInt(616445522, 696111111).toString(),
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



}