package com.curso.contacto.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.curso.contacto.databinding.ActivityDetailBinding
import com.curso.contacto.db.ContactoDatabase
import com.curso.contacto.db.entity.Contacto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {

    // 1. Corregido: El tipo debe ser ActivityDetailBinding
    private lateinit var binding: ActivityDetailBinding

    // 2. Corregido: Usamos el delegado lazy, no hace falta inicializar en onCreate
    private val db by lazy { ContactoDatabase.getDatabase(this) }
    private val contactoDao by lazy { db.contactoDao() }

    // 3. Corregido: Declarar la variable para el ID
    private var contactoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización correcta del binding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ID del intent
        contactoId = intent.getLongExtra("contactoId", -1)
        if (contactoId != -1L) {
            cargarDatosContacto()
        }

        configurarBotones()
    }

    private fun cargarDatosContacto() {
        lifecycleScope.launch {
            val contacto = withContext(Dispatchers.IO) {
                db.contactoDao().getById(contactoId)
            }
            contacto?.let {
                binding.etNombre.setText(it.nombre)
                binding.etApellidos.setText(it.apellidos)
                binding.etTelefono.setText(it.telefonoMovil)
                binding.etEmail.setText(it.email)
            }
        }
    }

    private fun configurarBotones() {
        // Botón Atrás
        binding.btnBack.setOnClickListener { finish() }

        // Botón Borrar
        binding.btnDelete.setOnClickListener {
            if (contactoId != -1L) {
                eliminarContacto()
            } else {
                finish() // Si es nuevo y no existe, solo cerramos
            }
        }

        // Botón Aceptar (Guardar o Actualizar)
        binding.btnAccept.setOnClickListener {
            guardarContacto()
        }
    }

    private fun guardarContacto() {
        val nombre = binding.etNombre.text.toString()
        val apellidos = binding.etApellidos.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val email = binding.etEmail.text.toString()

        if (nombre.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Nombre y teléfono son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        val entero: Int = 42

        val contacto = Contacto(
            id = if (contactoId == -1L) 0 else contactoId, // 0 para que Room genere el ID
            nombre = nombre,
            apellidos = apellidos,
            telefonoFijo = null,
            telefonoMovil = telefono,
            email = email,
            direccion = null,
            empresa = null,
            cumpleanos = null,
            fotoPerfilUri = null
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (contactoId == -1L) {
                    db.contactoDao().insert(contacto)
                } else {
                    db.contactoDao().update(contacto)
                }
            }
            Toast.makeText(this@DetailActivity, "Guardado correctamente", Toast.LENGTH_SHORT).show()
            finish() // Volver a la lista
        }
    }

    private fun eliminarContacto() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // Necesitamos el objeto completo para borrar o crear un método en el DAO que borre por ID
                val contacto = db.contactoDao().getById(contactoId)
                contacto?.let { db.contactoDao().delete(it) }
            }
            Toast.makeText(this@DetailActivity, "Contacto eliminado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
