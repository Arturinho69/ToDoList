package com.example.todolist_masco

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_masco.data.AppDatabase
import com.example.todolist_masco.ui.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = AppDatabase.getInstance(this).todoDao()

        val preferencias = getSharedPreferences(
            "sesion_todolist", MODE_PRIVATE
        )

        val usuarioId = preferencias.getLong("usuario", 0L)

        if (usuarioId != 0L && dao.usuarioPorId(usuarioId) != null) {
            startActivity(Intent(this, TareasActivity::class.java))
            finish()
            return
        }

        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnInicio = findViewById<Button>(R.id.btnInicio)
        val btnRegistro = findViewById<TextView>(R.id.btnRegistro)
        val btnRecuperar = findViewById<TextView>(R.id.btnRecuperar)

        btnInicio.setOnClickListener {
            val correo = txtUsuario.text.toString().trim().lowercase()
            val clave = txtContrasena.text.toString()

            val usuario = dao.usuarioPorCorreo(correo)

            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (usuario == null || usuario.clave != clave) {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            } else {
                preferencias.edit().putLong("usuario", usuario.id).apply()

                startActivity(Intent(this, TareasActivity::class.java))
                finish()
            }
        }

        btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        btnRecuperar.setOnClickListener {
            startActivity(Intent(this, RecuperarClaveActivity::class.java))
        }
    }
}
