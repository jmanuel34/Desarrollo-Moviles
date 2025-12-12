package com.curso.a10_ejemplobbdd_jm.db.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

    @ColumnInfo(defaultValue = "0")
    val age: Int,

    @ColumnInfo(name = "email", defaultValue = "NULL")
    val email: String?
) {
    @Ignore
    val fullName: String = "$firstName $lastName"
}