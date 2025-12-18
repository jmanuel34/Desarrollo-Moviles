package com.curso.contacto.ui

import android.R
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.curso.contacto.databinding.ActivityDetailBinding
import com.curso.contacto.db.ContactoDatabase
import com.curso.contacto.db.entity.Contacto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val db by lazy { ContactoDatabase.Companion.getDatabase(this) }
    private var contacto: Contacto? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contacto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_CONTACT", Contacto::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("EXTRA_CONTACT")
        }

        if (contacto != null) {
            populateUI(contacto!!)
            setFieldsEditable(false)
        } else {
            isEditMode = true
            setFieldsEditable(true)
            binding.tvEdit.visibility = View.GONE
        }

        configurarBotones()
    }

    private fun populateUI(c: Contacto) {
        binding.etNombre.setText(c.nombre)
        binding.etApellidos.setText(c.apellidos)
        binding.etTelefono.setText(c.telefonoMovil)
        binding.etEmail.setText(c.email)
    }

    private fun configurarBotones() {
        binding.btnBack.setOnClickListener { finish() }

        binding.tvEdit.setOnClickListener {
            if (!isEditMode) {
                isEditMode = true
                setFieldsEditable(true)
                Toast.makeText(this, "Modo edición activado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAccept.setOnClickListener {
            if (isEditMode) {
                mostrarDialogoConfirmacion("¿Desea guardar los cambios?", "Guardar") {
                    guardarOActualizarContacto()
                }
            } else {
                finish()
            }
        }

        binding.btnDelete.setOnClickListener {
            if (contacto != null) {
                mostrarDialogoConfirmacion("¿Está seguro de que desea eliminar este contacto?", "Eliminar") {
                    eliminarContacto()
                }
            } else {
                finish()
            }
        }
    }

    private fun setFieldsEditable(enabled: Boolean) {
        val fields = listOf(binding.etNombre, binding.etApellidos, binding.etTelefono, binding.etEmail)

        for (field in fields) {
            field.isEnabled = enabled
            val color = if (enabled) R.color.black else com.curso.contacto.R.color.gris_oscuro
            field.setTextColor(ContextCompat.getColor(this, color))
        }
    }

    private fun mostrarDialogoConfirmacion(mensaje: String, titulo: String, accion: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Sí") { _, _ -> accion() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun guardarOActualizarContacto() {
        val nuevoNombre = binding.etNombre.text.toString()
        val nuevoApellidos = binding.etApellidos.text.toString()
        val nuevoTelefono = binding.etTelefono.text.toString()
        val nuevoEmail = binding.etEmail.text.toString()

        if (nuevoNombre.isEmpty() || nuevoTelefono.isEmpty()) {
            Toast.makeText(this, "Nombre y teléfono son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val contactoEditado = Contacto(
            id = contacto?.id ?: 0,
            nombre = nuevoNombre,
            apellidos = nuevoApellidos,
            telefonoMovil = nuevoTelefono,
            telefonoFijo = null,
            direccion = null,
            empresa = null,
            email = nuevoEmail,
            cumpleanos = null,
            fotoPerfilUri = null
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (contacto == null) db.contactoDao().insert(contactoEditado)
                else db.contactoDao().update(contactoEditado)
            }
            finish()
        }
    }

    private fun eliminarContacto() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                contacto?.let { db.contactoDao().delete(it) }
            }
            finish()
        }
    }
}