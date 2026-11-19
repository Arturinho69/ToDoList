package com.example.todolist_masco

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todolist_masco.core.SessionManager
import com.example.todolist_masco.data.AppDatabase
import com.example.todolist_masco.ui.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = AppDatabase.getInstance(this).todoDao()
        val sessionManager = SessionManager(this)
        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)

        lifecycleScope.launch {
            val sesion = sessionManager.isLoggedIn.first()
            val usuarioId = sessionManager.userId.first()

            if (sesion && usuarioId != 0L && dao.usuarioPorId(usuarioId) != null) abrirTareas()
        }

        findViewById<Button>(R.id.btnInicio).setOnClickListener {
            val correo = txtUsuario.text.toString().trim().lowercase()
            val clave = txtContrasena.text.toString()
            val usuario = dao.usuarioPorCorreo(correo)

            when {
                correo.isEmpty() || clave.isEmpty() ->
                    aviso("Completa todos los campos")

                usuario == null || usuario.clave != clave ->
                    aviso("Correo o contraseña incorrectos")

                else -> lifecycleScope.launch {
                    sessionManager.saveSession(usuario.id, usuario.nombre)
                    abrirTareas()
                }
            }
        }

        findViewById<TextView>(R.id.btnRegistro).setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        findViewById<TextView>(R.id.btnRecuperar).setOnClickListener {
            startActivity(Intent(this, RecuperarClaveActivity::class.java))
        }
    }

    private fun abrirTareas() {
        startActivity(Intent(this, TareasActivity::class.java))
        finish()
    }

    private fun aviso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}