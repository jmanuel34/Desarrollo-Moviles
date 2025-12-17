import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.R
import androidx.recyclerview.widget.RecyclerView
import com.curso.contacto.db.entity.Contacto

import com.curso.contacto.databinding.ItemContactBinding

class ContactAdapter(private var contacts: List<Contacto>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    // Cambiamos View por el objeto Binding generado
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
        val contact = contacts[position]
        // Accedemos a las vistas directamente a través del binding

        holder.binding.contactName.text = contact.nombre
        holder.binding.contactPhone.text = contact.telefonoMovil
    }

    override fun getItemCount() = contacts.size

    fun updateData(newContacts: List<Contacto>) {
        this.contacts = newContacts
        notifyDataSetChanged()
    }
}