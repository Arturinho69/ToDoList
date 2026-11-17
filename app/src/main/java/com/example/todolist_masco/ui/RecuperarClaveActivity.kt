
package com.example.todolist_masco.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_masco.R
import com.example.todolist_masco.data.AppDatabase

class RecuperarClaveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_recuperar)

        val dao = AppDatabase.getInstance(this).todoDao()

        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtClaveNueva = findViewById<EditText>(R.id.txtClaveNueva)
        val btnRecuperar = findViewById<Button>(R.id.btnRecuperarClave)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        btnRecuperar.setOnClickListener {
            val correo = txtCorreo.text.toString().trim().lowercase()
            val clave = txtClaveNueva.text.toString()

            val usuario = dao.usuarioPorCorreo(correo)

            if (usuario == null) {
                Toast.makeText(this, "Cuenta no encontrada", Toast.LENGTH_SHORT).show()
            } else if (clave.length < 6) {
                Toast.makeText(this, "Usa mínimo 6 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                dao.cambiarClave(usuario.id, clave)

                Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }
}
