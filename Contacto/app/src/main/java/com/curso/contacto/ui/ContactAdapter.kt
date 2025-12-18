package com.curso.contacto.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.curso.contacto.databinding.ItemContactBinding
import com.curso.contacto.db.entity.Contacto
import com.curso.contacto.ui.DetailActivity
class ContactAdapter(private var contacts: List<Contacto>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        //1. Obtenemos el contacto actual de la lista
        val contacto = contacts[position]

        // 2. Asignamos los textos (lo que ya tenías)
        holder.binding.contactName.text = contacto.nombre
        holder.binding.contactPhone.text = contacto.telefonoMovil

        // Dentro de onBindViewHolder en ContactAdapter.kt
        holder.binding.moreOptions.setOnClickListener { view ->
            val context = view.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                // IMPORTANTE: La clave debe ser exactamente "EXTRA_CONTACT"
                // y pasamos el objeto 'contact' entero, no solo el id.
                putExtra("EXTRA_CONTACT", contacto)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = contacts.size

    fun updateData(newContacts: List<Contacto>) {
        this.contacts = newContacts
        notifyDataSetChanged()
    }
}
