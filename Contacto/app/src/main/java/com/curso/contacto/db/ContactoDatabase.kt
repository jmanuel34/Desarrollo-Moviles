package com.curso.contacto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.curso.contacto.db.dao.ContactoDao
import com.curso.contacto.db.entity.Contacto

@Database(entities = [Contacto::class], version = 1, exportSchema = false)
abstract class ContactoDatabase : RoomDatabase()  {

    // 2. Referencia al DAO
    // Room generará la implementación de este método en tiempo de compilación.
    abstract fun contactoDao(): ContactoDao

    // 3. Companion Object (Singleton Pattern)
    // Asegura que solo exista una instancia de la base de datos.
    companion object {
        // @Volatile asegura que el valor de INSTANCE siempre esté actualizado
        // y sea visible para todos los hilos.
        @Volatile
        private var INSTANCE: ContactoDatabase? = null

        // Función para obtener la instancia de la base de datos
        fun getDatabase(context: Context): ContactoDatabase {
            // Si la INSTANCE no es nula, devuélvela (ya existe).
            return INSTANCE ?: synchronized(this) {
                // Si es nula, crea la base de datos dentro del bloque sincronizado
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactoDatabase::class.java,
                    "nombre_de_mi_app_db" // 4. Nombre del archivo de la base de datos
                )
                    // Esto permite que el builder se ejecute en el hilo principal
                    // (solo para pruebas, generalmente no recomendado en producción).
                    // .allowMainThreadQueries()

                    // Estrategia de migración simple: recrea la base de datos si la versión cambia
                    // (pierdes los datos, pero es fácil para empezar).
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                // Retorna la instancia
                instance
            }
        }
    }

}