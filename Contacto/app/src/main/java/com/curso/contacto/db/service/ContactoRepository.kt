package com.curso.contacto.db.service
import com.curso.contacto.db.dao.ContactoDao
import com.curso.contacto.db.entity.Contacto


class ContactoRepository (private val contactoDao: ContactoDao){
    // 1. READ (Lectura): No es 'suspend' porque Flow ya maneja la asincronía.
    // Proporciona la lista de contactos como un Flow reactivo.
    val todosLosContactos = contactoDao.getAll()


    // 2. CREATE (Creación): Debe ser 'suspend' para ejecutar en un hilo de fondo.
    suspend fun insertar(contacto: Contacto) {
        contactoDao.insert(contacto)
    }

    // 3. UPDATE (Actualización): Debe ser 'suspend'.
    suspend fun actualizar(contacto: Contacto) {
        contactoDao.update(contacto)
    }

    // 4. DELETE (Eliminación): Debe ser 'suspend'.
    suspend fun eliminar(contacto: Contacto) {
        contactoDao.delete(contacto)
    }

    // Opcional: Obtener por ID
    suspend fun obtenerPorId(id: Long): Contacto? {
        return contactoDao.getById(id)
    }
}