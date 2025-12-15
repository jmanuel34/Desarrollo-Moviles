package com.curso.a10_ejemplobbdd_jm.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    @ColumnInfo(name = "nombre")
    val firstName: String?,

    @ColumnInfo(name = "apellidos")
    val lastName: String?,

    //defaultValue debe ser una constante
    @ColumnInfo(name = "edad", defaultValue = "0")
    val age: Int,

    @ColumnInfo(defaultValue = "NULL")
    val email: String? = null,

    //@ColumnInfo()
    // val email: String? = null,

    // @ColumnInfo(defaultValue = "'DESCONOCIDO'")
    // val email: String,

    @ColumnInfo(name = "fecha_creacion")
    val date: Long? = null,

    @ColumnInfo(name = "avatar")
    val image: ByteArray? = null, //No almacenar Blob muy grandes, mejor usar un enlace a las imágenes o comprimir las imágenes.

) {
    // Room no persistirá este campo porque no está en el constructor primario
    // y está anotado con @Ignore
    @Ignore
    val fullName: String = "$firstName $lastName"
}