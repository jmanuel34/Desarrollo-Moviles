package com.curso.a10_ejemplobbdd_jm.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.curso.a10_ejemplobbdd_jm.db.entity.User
import javax.`annotation`.processing.Generated
import kotlin.ByteArray
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class UserDao_Impl(
  __db: RoomDatabase,
) : UserDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUser: EntityInsertAdapter<User>

  private val __deleteAdapterOfUser: EntityDeleteOrUpdateAdapter<User>

  private val __updateAdapterOfUser: EntityDeleteOrUpdateAdapter<User>
  init {
    this.__db = __db
    this.__insertAdapterOfUser = object : EntityInsertAdapter<User>() {
      protected override fun createQuery(): String = "INSERT OR IGNORE INTO `user` (`uid`,`nombre`,`apellidos`,`edad`,`email`,`fecha_creacion`,`avatar`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: User) {
        statement.bindLong(1, entity.uid.toLong())
        val _tmpFirstName: String? = entity.firstName
        if (_tmpFirstName == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpFirstName)
        }
        val _tmpLastName: String? = entity.lastName
        if (_tmpLastName == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpLastName)
        }
        statement.bindLong(4, entity.age.toLong())
        val _tmpEmail: String? = entity.email
        if (_tmpEmail == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpEmail)
        }
        statement.bindLong(6, entity.date)
        val _tmpImage: ByteArray? = entity.image
        if (_tmpImage == null) {
          statement.bindNull(7)
        } else {
          statement.bindBlob(7, _tmpImage)
        }
      }
    }
    this.__deleteAdapterOfUser = object : EntityDeleteOrUpdateAdapter<User>() {
      protected override fun createQuery(): String = "DELETE FROM `user` WHERE `uid` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: User) {
        statement.bindLong(1, entity.uid.toLong())
      }
    }
    this.__updateAdapterOfUser = object : EntityDeleteOrUpdateAdapter<User>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `user` SET `uid` = ?,`nombre` = ?,`apellidos` = ?,`edad` = ?,`email` = ?,`fecha_creacion` = ?,`avatar` = ? WHERE `uid` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: User) {
        statement.bindLong(1, entity.uid.toLong())
        val _tmpFirstName: String? = entity.firstName
        if (_tmpFirstName == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpFirstName)
        }
        val _tmpLastName: String? = entity.lastName
        if (_tmpLastName == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpLastName)
        }
        statement.bindLong(4, entity.age.toLong())
        val _tmpEmail: String? = entity.email
        if (_tmpEmail == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpEmail)
        }
        statement.bindLong(6, entity.date)
        val _tmpImage: ByteArray? = entity.image
        if (_tmpImage == null) {
          statement.bindNull(7)
        } else {
          statement.bindBlob(7, _tmpImage)
        }
        statement.bindLong(8, entity.uid.toLong())
      }
    }
  }

  public override suspend fun insert(user: User): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfUser.insert(_connection, user)
  }

  public override suspend fun delete(user: User): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfUser.handle(_connection, user)
  }

  public override suspend fun update(user: User): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfUser.handle(_connection, user)
  }

  public override fun getAllUsers(): Flow<List<User>> {
    val _sql: String = "SELECT * FROM user ORDER BY apellidos ASC"
    return createFlow(__db, false, arrayOf("user")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfUid: Int = getColumnIndexOrThrow(_stmt, "uid")
        val _columnIndexOfFirstName: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfLastName: Int = getColumnIndexOrThrow(_stmt, "apellidos")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "edad")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "fecha_creacion")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "avatar")
        val _result: MutableList<User> = mutableListOf()
        while (_stmt.step()) {
          val _item: User
          val _tmpUid: Int
          _tmpUid = _stmt.getLong(_columnIndexOfUid).toInt()
          val _tmpFirstName: String?
          if (_stmt.isNull(_columnIndexOfFirstName)) {
            _tmpFirstName = null
          } else {
            _tmpFirstName = _stmt.getText(_columnIndexOfFirstName)
          }
          val _tmpLastName: String?
          if (_stmt.isNull(_columnIndexOfLastName)) {
            _tmpLastName = null
          } else {
            _tmpLastName = _stmt.getText(_columnIndexOfLastName)
          }
          val _tmpAge: Int
          _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          val _tmpEmail: String?
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          }
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfImage)) {
            _tmpImage = null
          } else {
            _tmpImage = _stmt.getBlob(_columnIndexOfImage)
          }
          _item = User(_tmpUid,_tmpFirstName,_tmpLastName,_tmpAge,_tmpEmail,_tmpDate,_tmpImage)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUserById(uid: Int): Flow<User> {
    val _sql: String = "SELECT * FROM user WHERE uid = ?"
    return createFlow(__db, false, arrayOf("user")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, uid.toLong())
        val _columnIndexOfUid: Int = getColumnIndexOrThrow(_stmt, "uid")
        val _columnIndexOfFirstName: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfLastName: Int = getColumnIndexOrThrow(_stmt, "apellidos")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "edad")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "fecha_creacion")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "avatar")
        val _result: User
        if (_stmt.step()) {
          val _tmpUid: Int
          _tmpUid = _stmt.getLong(_columnIndexOfUid).toInt()
          val _tmpFirstName: String?
          if (_stmt.isNull(_columnIndexOfFirstName)) {
            _tmpFirstName = null
          } else {
            _tmpFirstName = _stmt.getText(_columnIndexOfFirstName)
          }
          val _tmpLastName: String?
          if (_stmt.isNull(_columnIndexOfLastName)) {
            _tmpLastName = null
          } else {
            _tmpLastName = _stmt.getText(_columnIndexOfLastName)
          }
          val _tmpAge: Int
          _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          val _tmpEmail: String?
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          }
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfImage)) {
            _tmpImage = null
          } else {
            _tmpImage = _stmt.getBlob(_columnIndexOfImage)
          }
          _result = User(_tmpUid,_tmpFirstName,_tmpLastName,_tmpAge,_tmpEmail,_tmpDate,_tmpImage)
        } else {
          error("The query result was empty, but expected a single row to return a NON-NULL object of type 'com.curso.a10_ejemplobbdd_jm.db.entity.User'.")
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAdultUsers(): Flow<List<User>> {
    val _sql: String = "SELECT * FROM user WHERE edad >= 18"
    return createFlow(__db, false, arrayOf("user")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfUid: Int = getColumnIndexOrThrow(_stmt, "uid")
        val _columnIndexOfFirstName: Int = getColumnIndexOrThrow(_stmt, "nombre")
        val _columnIndexOfLastName: Int = getColumnIndexOrThrow(_stmt, "apellidos")
        val _columnIndexOfAge: Int = getColumnIndexOrThrow(_stmt, "edad")
        val _columnIndexOfEmail: Int = getColumnIndexOrThrow(_stmt, "email")
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "fecha_creacion")
        val _columnIndexOfImage: Int = getColumnIndexOrThrow(_stmt, "avatar")
        val _result: MutableList<User> = mutableListOf()
        while (_stmt.step()) {
          val _item: User
          val _tmpUid: Int
          _tmpUid = _stmt.getLong(_columnIndexOfUid).toInt()
          val _tmpFirstName: String?
          if (_stmt.isNull(_columnIndexOfFirstName)) {
            _tmpFirstName = null
          } else {
            _tmpFirstName = _stmt.getText(_columnIndexOfFirstName)
          }
          val _tmpLastName: String?
          if (_stmt.isNull(_columnIndexOfLastName)) {
            _tmpLastName = null
          } else {
            _tmpLastName = _stmt.getText(_columnIndexOfLastName)
          }
          val _tmpAge: Int
          _tmpAge = _stmt.getLong(_columnIndexOfAge).toInt()
          val _tmpEmail: String?
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail)
          }
          val _tmpDate: Long
          _tmpDate = _stmt.getLong(_columnIndexOfDate)
          val _tmpImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfImage)) {
            _tmpImage = null
          } else {
            _tmpImage = _stmt.getBlob(_columnIndexOfImage)
          }
          _item = User(_tmpUid,_tmpFirstName,_tmpLastName,_tmpAge,_tmpEmail,_tmpDate,_tmpImage)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
