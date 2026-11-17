package com.example.todolist_masco.ui

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_masco.R
import com.example.todolist_masco.data.AppDatabase
import com.example.todolist_masco.data.TodoDao

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var dao: TodoDao
    private var usuarioId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_editar_perfil)

        dao = AppDatabase.getInstance(this).todoDao()

        usuarioId = getSharedPreferences("sesion_todolist", MODE_PRIVATE)
            .getLong("usuario", 0L)

        if (usuarioId == 0L) {
            finish()
            return
        }

        val usuario = dao.usuarioPorId(usuarioId)

        if (usuario == null) {
            finish()
            return
        }

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)

        txtNombre.setText(usuario.nombre)
        txtCorreo.setText(usuario.correo)

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {

            val nombre = txtNombre.text.toString().trim()
            val correo = txtCorreo.text.toString().trim().lowercase()

            when {
                nombre.isBlank() ->
                    txtNombre.error = "Escribe tu nombre"

                nombre.length < 3 ->
                    txtNombre.error = "Mínimo 3 caracteres"

                !Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                    txtCorreo.error = "Escribe un correo válido"

                dao.usuarioPorCorreo(correo)?.id != null &&
                        dao.usuarioPorCorreo(correo)?.id != usuarioId ->
                    txtCorreo.error = "Ese correo ya está registrado"

                else -> {
                    dao.editarUsuario(usuarioId, nombre, correo)

                    Toast.makeText(
                        this,
                        "Perfil actualizado",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }

        findViewById<Button>(R.id.btnAtras).setOnClickListener {
            finish()
        }
    }
}