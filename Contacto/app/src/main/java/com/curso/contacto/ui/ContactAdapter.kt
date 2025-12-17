import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.R
import androidx.recyclerview.widget.RecyclerView
import com.curso.contacto.db.entity.Contacto

import com.curso.contacto.databinding.ItemContactBinding
import com.curso.contacto.ui.DetailActivity

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

        holder.binding.contactName.text = contact.nombre
        holder.binding.contactPhone.text = contact.telefonoMovil

        holder.binding.moreOptions.setOnClickListener { view ->
            val context = view.context
            val intent = Intent(context, DetailActivity::class.java)

            // IMPORTANTE: Asegúrate de que el ID sea el mismo tipo que en DetailActivity
            // Si usas Long, usa putExtra("CONTACT_ID", contact.id.toLong())
            intent.putExtra("CONTACT_ID", contact.id)

            context.startActivity(intent)
        }
    }


    override fun getItemCount() = contacts.size

    fun updateData(newContacts: List<Contacto>) {
        this.contacts = newContacts
        notifyDataSetChanged()
    }
}