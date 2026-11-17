
package com.example.todolist_masco.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_masco.R

class NotificacionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_notificaciones)

        val preferencias = getSharedPreferences(
            "sesion_todolist", MODE_PRIVATE
        )

        val swRecordatorios = findViewById<Switch>(R.id.swRecordatorios)
        val swPrioritarias = findViewById<Switch>(R.id.swPrioritarias)
        val swResumen = findViewById<Switch>(R.id.swResumen)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        swRecordatorios.isChecked =
            preferencias.getBoolean("recordatorios", true)

        swPrioritarias.isChecked =
            preferencias.getBoolean("prioritarias", true)

        swResumen.isChecked =
            preferencias.getBoolean("resumen", false)

        swRecordatorios.setOnCheckedChangeListener { _, activo ->
            preferencias.edit().putBoolean("recordatorios", activo).apply()
        }

        swPrioritarias.setOnCheckedChangeListener { _, activo ->
            preferencias.edit().putBoolean("prioritarias", activo).apply()
        }

        swResumen.setOnCheckedChangeListener { _, activo ->
            preferencias.edit().putBoolean("resumen", activo).apply()
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }
}
