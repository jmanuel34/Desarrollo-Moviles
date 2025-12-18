package com.curso.contacto.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "contactos")
data class Contacto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
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
    val cumpleanos: String?,
    @ColumnInfo(name = "foto_perfil_uri")
    val fotoPerfilUri: String?
) : Parcelable