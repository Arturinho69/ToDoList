package com.example.todolist_masco.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todolist_masco.MainActivity
import com.example.todolist_masco.R
import com.example.todolist_masco.data.AppDatabase
import kotlinx.coroutines.launch
import com.example.todolist_masco.core.SessionManager
import kotlinx.coroutines.flow.first

class PerfilActivity : AppCompatActivity() {

    private val dao by lazy { AppDatabase.getInstance(this).todoDao() }
    private val sessionManager by lazy { SessionManager(this) }
    private var usuarioId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            usuarioId = sessionManager.userId.first()

            if (usuarioId == 0L) {
                finish()
                return@launch
            }

            mostrarPerfil()
        }
    }

    override fun onResume() {
        super.onResume()
        if (usuarioId != 0L) mostrarPerfil()
    }

    private fun mostrarPerfil() {
        setContentView(R.layout.pantalla_perfil)

        val usuario = dao.usuarioPorId(usuarioId)
        if (usuario == null) {
            finish()
            return
        }

        val tareas = dao.tareas(usuarioId)

        findViewById<TextView>(R.id.lblUsuario).text = usuario.nombre
        findViewById<TextView>(R.id.lblCorreo).text = usuario.correo
        findViewById<TextView>(R.id.lblEstadisticas).text =
            "${tareas.size} tareas\n" +
                    "${tareas.count { !it.completada }} pendientes\n" +
                    "${tareas.count { it.completada }} completadas"

        findViewById<Button>(R.id.btnEditar).setOnClickListener {
            startActivity(Intent(this, EditarPerfilActivity::class.java))
        }

        findViewById<Button>(R.id.btnNotificaciones).setOnClickListener {
            startActivity(Intent(this, ConfiguracionActivity::class.java))
        }

        findViewById<Button>(R.id.btnCambiarClave).setOnClickListener {
            startActivity(Intent(this, CambiarClaveActivity::class.java))
        }

        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            lifecycleScope.launch {
                sessionManager.logout()

                val intent = Intent(this@PerfilActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        configurarBarra()
    }

    private fun configurarBarra() {
        val barra = findViewById<LinearLayout>(R.id.barraNavegacion)
        barra.removeAllViews()

        val opciones = listOf("Tareas", "Pendientes", "Categorías", "Perfil")

        for (i in opciones.indices) {
            val boton = TextView(this)
            boton.text = opciones[i]
            boton.textSize = 12f
            boton.gravity = Gravity.CENTER
            boton.setTextColor(Color.parseColor("#7048AB"))

            barra.addView(
                boton,
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            )

            boton.setOnClickListener {
                when (i) {
                    0 -> {
                        startActivity(Intent(this, TareasActivity::class.java))
                        finish()
                    }

                    1 -> {
                        val intent = Intent(this, TareasActivity::class.java)
                        intent.putExtra("pendientes", true)
                        startActivity(intent)
                        finish()
                    }

                    2 -> {
                        startActivity(Intent(this, CategoriasActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}