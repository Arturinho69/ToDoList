package com.example.todolist_masco.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Entity(tableName = "usuarios", indices = [Index(value = ["correo"], unique = true)])
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val correo: String,
    val clave: String
)

@Entity(tableName = "categorias", indices = [Index(value = ["usuarioId", "nombre"], unique = true)])
data class Categoria(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val usuarioId: Long,
    val nombre: String,
    val color: String = "#7048AB"
)

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val usuarioId: Long,
    val nombre: String,
    val descripcion: String = "",
    val categoria: String = "Personal",
    val completada: Boolean = false,
    val prioridad: Boolean = false,
    val estado: String = "Pendiente"
)

@Dao interface TodoDao {
    @Insert fun insertarUsuario(usuario: Usuario): Long
    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1") fun usuarioPorCorreo(correo: String): Usuario?
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1") fun usuarioPorId(id: Long): Usuario?
    @Query("UPDATE usuarios SET nombre=:nombre, correo=:correo WHERE id=:id") fun editarUsuario(id: Long, nombre: String, correo: String)
    @Query("UPDATE usuarios SET clave=:clave WHERE id=:id") fun cambiarClave(id: Long, clave: String)
    @Insert fun insertarCategoria(categoria: Categoria): Long
    @Query("SELECT * FROM categorias WHERE usuarioId = :usuarioId ORDER BY id") fun categorias(usuarioId: Long): List<Categoria>
    @Query("DELETE FROM categorias WHERE id=:id AND usuarioId=:usuarioId") fun eliminarCategoria(id: Long, usuarioId: Long)
    @Insert fun insertarTarea(tarea: Tarea): Long
    @Update fun actualizarTarea(tarea: Tarea)
    @Delete fun eliminarTarea(tarea: Tarea)
    @Query("SELECT * FROM tareas WHERE usuarioId=:usuarioId ORDER BY completada ASC, prioridad DESC, id DESC") fun tareas(usuarioId: Long): List<Tarea>
}

@Database(entities = [Usuario::class, Categoria::class, Tarea::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        private val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE tareas ADD COLUMN estado TEXT NOT NULL DEFAULT 'Pendiente'"
                )

                db.execSQL(
                    "UPDATE tareas SET estado = 'Completada' WHERE completada = 1"
                )
            }
        }

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "todolist_masco.db")
                .addMigrations(MIGRACION_1_2)
                .allowMainThreadQueries() // Demo escolar; para producción usar corrutinas.
                .build().also { INSTANCE = it }
        }
    }
}
