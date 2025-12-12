package com.curso.a10_ejemplobbdd_jm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user ORDER BY first_name ASC")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM user WHERE uid = :uid")
    fun getUserById(uid: Int): User?
}