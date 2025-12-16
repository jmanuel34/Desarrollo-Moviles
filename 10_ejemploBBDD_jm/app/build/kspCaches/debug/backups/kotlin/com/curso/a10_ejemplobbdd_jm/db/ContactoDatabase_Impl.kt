package com.curso.a10_ejemplobbdd_jm.db

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.curso.a10_ejemplobbdd_jm.db.dao.UserDao
import com.curso.a10_ejemplobbdd_jm.db.dao.UserDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ContactoDatabase_Impl : ContactoDatabase() {
  private val _userDao: Lazy<UserDao> = lazy {
    UserDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "af01df116ca86f111fec699e93818e18", "3f9f67df8855fdd645cbee0f3fcb5d7d") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `user` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT, `apellidos` TEXT, `edad` INTEGER NOT NULL DEFAULT 0, `email` TEXT DEFAULT NULL, `fecha_creacion` INTEGER NOT NULL, `avatar` BLOB)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'af01df116ca86f111fec699e93818e18')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `user`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsUser: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUser.put("uid", TableInfo.Column("uid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUser.put("nombre", TableInfo.Column("nombre", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUser.put("apellidos", TableInfo.Column("apellidos", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUser.put("edad", TableInfo.Column("edad", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsUser.put("email", TableInfo.Column("email", "TEXT", false, 0, "NULL", TableInfo.CREATED_FROM_ENTITY))
        _columnsUser.put("fecha_creacion", TableInfo.Column("fecha_creacion", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUser.put("avatar", TableInfo.Column("avatar", "BLOB", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUser: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUser: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUser: TableInfo = TableInfo("user", _columnsUser, _foreignKeysUser, _indicesUser)
        val _existingUser: TableInfo = read(connection, "user")
        if (!_infoUser.equals(_existingUser)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |user(com.curso.a10_ejemplobbdd_jm.db.entity.User).
              | Expected:
              |""".trimMargin() + _infoUser + """
              |
              | Found:
              |""".trimMargin() + _existingUser)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "user")
  }

  public override fun clearAllTables() {
    super.performClear(false, "user")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(UserDao::class, UserDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun userDao(): UserDao = _userDao.value
}
