package com.curso.contacto

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.curso.contacto.databinding.ActivityMainBinding
import com.curso.contacto.db.ContactoDatabase
import com.curso.contacto.ui.DetailActivity
import com.curso.contacto.db.dao.ContactoDao
import com.curso.contacto.db.entity.Contacto
import com.curso.contacto.ui.ContactAdapter
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db by lazy { ContactoDatabase.getDatabase(this) }
    private val contactoDao by lazy { db.contactoDao() }
    private val faker = Faker()
    
    private val adapter: ContactAdapter by lazy {
        ContactAdapter(emptyList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AJUSTE PARA EVITAR SUPERPOSICIÓN CON BARRA DE ESTADO (Hora, WiFi, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = insets.top)
            windowInsets
        }

        setupRecyclerView()

        // Pulsación corta: Ir a DetailActivity para añadir manualmente
        binding.fabAddContact.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        // Pulsación larga: Generar 10 usuarios ficticios
        binding.fabAddContact.setOnLongClickListener {
            lifecycleScope.launch {
                val fakes = generateFakeUsers(10)
                withContext(Dispatchers.IO) {
                    fakes.forEach { contactoDao.insert(it) }
                }
                Toast.makeText(this@MainActivity, "10 contactos ficticios generados", Toast.LENGTH_SHORT).show()
            }
            true // Indica que el evento ha sido consumido
        }

        lifecycleScope.launch {
            contactoDao.getAll().collect { listaContactos ->
                Log.i("Datos", "Contactos actualizados: ${listaContactos.size}")
                adapter.updateData(listaContactos)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewContacts.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateFakeUsers(count: Int): List<Contacto> {
        val userList = mutableListOf<Contacto>()
        repeat(count) {
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
        val minYear = 1970
        val maxYear = 2025
        val randomYear = random.nextInt(maxYear - minYear + 1) + minYear
        val randomDayOfYear = random.nextInt(365) + 1
        calendar.set(Calendar.YEAR, randomYear)
        calendar.set(Calendar.DAY_OF_YEAR, randomDayOfYear)
        val sdf = SimpleDateFormat(formato, Locale.getDefault())
        return sdf.format(calendar.time)
    }
}
