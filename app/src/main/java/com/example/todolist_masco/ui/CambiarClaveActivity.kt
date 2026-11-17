
package com.example.todolist_masco.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_masco.R
import com.example.todolist_masco.data.AppDatabase
import com.example.todolist_masco.data.TodoDao

class CambiarClaveActivity : AppCompatActivity() {

    private lateinit var dao: TodoDao
    private var usuarioId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_cambiar_clave)

        dao = AppDatabase.getInstance(this).todoDao()

        usuarioId = getSharedPreferences("sesion_todolist", MODE_PRIVATE)
            .getLong("usuario", 0L)

        if (usuarioId == 0L) {
            finish()
            return
        }

        val txtActual = findViewById<EditText>(R.id.txtActual)
        val txtNueva = findViewById<EditText>(R.id.txtNueva)
        val txtConfirmar = findViewById<EditText>(R.id.txtConfirmar)
        val btnCambiar = findViewById<Button>(R.id.btnCambiar)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        btnCambiar.setOnClickListener {
            val actual = txtActual.text.toString()
            val nueva = txtNueva.text.toString()
            val confirmar = txtConfirmar.text.toString()

            val usuario = dao.usuarioPorId(usuarioId)

            if (usuario == null) {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()

            } else if (usuario.clave != actual) {
                Toast.makeText(this, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()

            } else if (nueva.length < 6) {
                Toast.makeText(this, "La contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show()

            } else if (nueva != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()

            } else {
                dao.cambiarClave(usuarioId, nueva)

                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }
}
