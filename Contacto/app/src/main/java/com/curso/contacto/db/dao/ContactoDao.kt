package com.curso.contacto.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.curso.contacto.db.entity.Contacto
import kotlinx.coroutines.flow.Flow
@Dao
interface ContactoDao {

    // ------------------------------------
    // C - CREATE (Crear) / Insertar
    // ------------------------------------
    // Inserta un nuevo contacto en la base de datos.
    // OnConflictStrategy.REPLACE: Si se intenta insertar un contacto con el mismo
    // ID (algo que solo pasaría si no usáramos autoGenerate o si lo forzáramos),
    // reemplaza el registro existente.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contacto: Contacto)

    // ------------------------------------
    // R - READ (Leer) / Consultar
    // ------------------------------------

    // Obtener TODOS los contactos, ordenados por apellidos.
    // Flow o LiveData son recomendados para bases de datos reactivas,
    // ya que emiten automáticamente un nuevo valor cuando los datos cambian.
    @Query("SELECT * FROM contactos ORDER BY apellidos ASC")
    fun getAll(): Flow<List<Contacto>>

    // Obtener un contacto específico por su ID.
    @Query("SELECT * FROM contactos WHERE id = :contactoId")
    suspend fun getById(contactoId: Long): Contacto?

    // ------------------------------------
    // U - UPDATE (Actualizar)
    // ------------------------------------
    // Actualiza un contacto existente. Room usa la Primary Key para saber
    // qué registro actualizar.
    @Update
    suspend fun update(contacto: Contacto)

    // ------------------------------------
    // D - DELETE (Borrar) / Eliminar
    // ------------------------------------
    // Elimina un contacto basándose en el objeto Contacto que se le pasa.
    @Delete
    suspend fun delete(contacto: Contacto)

    // Opción: Eliminar un contacto por su ID (usando una Query)
    @Query("DELETE FROM contactos WHERE id = :contactoId")
    suspend fun deleteById(contactoId: Long)
}