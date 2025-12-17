package com.curso.contacto.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 1. ANOTACIÓN @Entity
// Indica que esta clase representa una tabla en la base de datos SQLite.
// El 'tableName' es opcional, pero ayuda a dar un nombre descriptivo a la tabla.
@Entity(tableName = "contactos")
data class Contacto(

    // 2. ANOTACIÓN @PrimaryKey
    // Es esencial. Room necesita una clave primaria para identificar cada fila.
    // 'autoGenerate = true' indica que la base de datos generará automáticamente
    // un ID único para cada nuevo contacto insertado.
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Usamos 'Long' para el ID y lo inicializamos a 0

    // 3. ANOTACIONES @ColumnInfo
    // Opcional, pero se utiliza para definir nombres de columna distintos
    // a los nombres de la variable.
    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "apellidos")
    val apellidos: String,

    @ColumnInfo(name = "telefono_fijo")
    val telefonoFijo: String?,

    @ColumnInfo(name = "telefono_movil")
    val telefonoMovil: String,

    @ColumnInfo(name = "direccion")
    val direccion: String?,

    @ColumnInfo(name = "empresa")
    val empresa: String?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "cumpleanos")
    val cumpleanos: String?, // Recomendación: Si quieres manejarlo como fecha (Date/Calendar)
    // de forma avanzada, necesitarás un TypeConverter.

    @ColumnInfo(name = "foto_perfil_uri")
    val fotoPerfilUri: String? // Almacenaremos la URI como String
)