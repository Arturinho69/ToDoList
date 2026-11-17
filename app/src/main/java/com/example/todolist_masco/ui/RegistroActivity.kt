
package com.example.todolist_masco.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_masco.MainActivity
import com.example.todolist_masco.R
import com.example.todolist_masco.data.*

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_registro)

        val dao = AppDatabase.getInstance(this).todoDao()

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtClave = findViewById<EditText>(R.id.txtClave)
        val txtConfirmar = findViewById<EditText>(R.id.txtConfirmar)

        findViewById<Button>(R.id.btnRegistrar).setOnClickListener {

            val nombre = txtNombre.text.toString().trim()
            val correo = txtCorreo.text.toString().trim().lowercase()
            val clave = txtClave.text.toString()
            val confirmar = txtConfirmar.text.toString()

            when {
                nombre.isBlank() ->
                    txtNombre.error = "Escribe tu nombre"

                nombre.length < 3 ->
                    txtNombre.error = "Mínimo 3 caracteres"

                !Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                    txtCorreo.error = "Escribe un correo válido"

                dao.usuarioPorCorreo(correo) != null ->
                    txtCorreo.error = "Ese correo ya está registrado"

                clave.length < 6 ->
                    txtClave.error = "Mínimo 6 caracteres"

                clave.isBlank() ->
                    txtClave.error = "Escribe una contraseña"

                clave != confirmar ->
                    txtConfirmar.error = "Las contraseñas no coinciden"

                else -> {
                    val id = dao.insertarUsuario(
                        Usuario(
                            nombre = nombre,
                            correo = correo,
                            clave = clave
                        )
                    )

                    for (categoria in listOf("Personal", "Hogar", "Escuela")) {
                        dao.insertarCategoria(
                            Categoria(usuarioId = id, nombre = categoria)
                        )
                    }

                    getSharedPreferences("sesion_todolist", MODE_PRIVATE)
                        .edit().putLong("usuario", id).apply()

                    Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish()
                }
            }
        }

        findViewById<TextView>(R.id.btnVolver).setOnClickListener {
            finish()
        }
    }
}
