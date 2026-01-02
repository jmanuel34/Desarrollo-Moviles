package com.curso.contacto.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.curso.contacto.R
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
    private var isEditMode = false
    private var newImageUri: String? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            try {
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                contentResolver.takePersistableUriPermission(it, takeFlags)
            } catch (e: SecurityException) {
                e.printStackTrace()
                Toast.makeText(this, "No se pudo guardar el acceso a la imagen", Toast.LENGTH_SHORT).show()
            }

            newImageUri = it.toString()
            binding.profileImage.load(it) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

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
            binding.profileImage.load(android.R.drawable.ic_menu_gallery) {
                transformations(CircleCropTransformation())
            }
        }

        configurarBotones()
    }

    private fun populateUI(c: Contacto) {
        binding.etNombre.setText(c.nombre)
        binding.etApellidos.setText(c.apellidos)
        binding.etTelefono.setText(c.telefonoMovil)
        binding.etEmail.setText(c.email)

        // --- TEMPORARILY DISABLED FOR DEBUGGING ---
        // The following code is temporarily disabled to diagnose the crash.
        /*
        try {
            val imageUriString = c.fotoPerfilUri
            if (imageUriString.isNullOrBlank()) {
                binding.profileImage.load(android.R.drawable.ic_menu_gallery) {
                    transformations(CircleCropTransformation())
                }
            } else {
                binding.profileImage.load(Uri.parse(imageUriString)) {
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                    error(android.R.drawable.ic_menu_gallery)
                    transformations(CircleCropTransformation())
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            binding.profileImage.load(android.R.drawable.ic_menu_gallery) {
                transformations(CircleCropTransformation())
            }
            Toast.makeText(this, "No se pudo cargar la imagen. Es posible que el permiso se haya perdido.", Toast.LENGTH_LONG).show()
        }
        */
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

        val changePhotoAction = { 
            if (isEditMode) {
                selectImageLauncher.launch("image/*")
            } else {
                Toast.makeText(this, "Pulse Editar para poder cambiar la foto", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabChangePhoto.setOnClickListener { changePhotoAction() }
        binding.tvChangePhoto.setOnClickListener { changePhotoAction() }
    }

    private fun setFieldsEditable(enabled: Boolean) {
        val fields = listOf(binding.etNombre, binding.etApellidos, binding.etTelefono, binding.etEmail)

        for (field in fields) {
            field.isEnabled = enabled
            val color = if (enabled) R.color.black else R.color.gris_oscuro
            field.setTextColor(ContextCompat.getColor(this, color))
        }

        binding.fabChangePhoto.visibility = if (enabled) View.VISIBLE else View.GONE
        binding.tvChangePhoto.visibility = if (enabled) View.VISIBLE else View.GONE
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

        val fotoUri = newImageUri ?: contacto?.fotoPerfilUri

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
            fotoPerfilUri = fotoUri
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