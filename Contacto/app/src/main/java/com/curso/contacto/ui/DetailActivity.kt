package com.curso.contacto.ui

import android.os.Build
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

    private lateinit var binding: ActivityDetailBinding
    private val db by lazy { ContactoDatabase.getDatabase(this) }
    private var contacto: Contacto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el objeto Contacto
        contacto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_CONTACT", Contacto::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EXTRA_CONTACT")
        }

        // Si tenemos un contacto, cargamos sus datos en la UI
        contacto?.let { populateUI(it) }

        configurarBotones()
    }

    private fun populateUI(contacto: Contacto) {
        binding.etNombre.setText(contacto.nombre)
        binding.etApellidos.setText(contacto.apellidos)
        binding.etTelefono.setText(contacto.telefonoMovil)
        binding.etEmail.setText(contacto.email)
    }

    private fun configurarBotones() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnDelete.setOnClickListener {
            contacto?.let { eliminarContacto(it) } ?: finish()
        }

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

        val contactoParaGuardar = Contacto(
            id = contacto?.id ?: 0, // Si el contacto es nuevo, id es 0 para que Room lo genere
            nombre = nombre,
            apellidos = apellidos,
            telefonoFijo = contacto?.telefonoFijo,
            telefonoMovil = telefono,
            email = email,
            direccion = contacto?.direccion,
            empresa = contacto?.empresa,
            cumpleanos = contacto?.cumpleanos,
            fotoPerfilUri = contacto?.fotoPerfilUri
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (contactoParaGuardar.id == 0L) {
                    db.contactoDao().insert(contactoParaGuardar)
                } else {
                    db.contactoDao().update(contactoParaGuardar)
                }
            }
            val message = if (contacto == null) "Guardado correctamente" else "Actualizado correctamente"
            Toast.makeText(this@DetailActivity, message, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun eliminarContacto(contacto: Contacto) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.contactoDao().delete(contacto)
            }
            Toast.makeText(this@DetailActivity, "Contacto eliminado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
