package com.example.todolist_masco.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.todolist_masco.R
import com.example.todolist_masco.data.Preferencias
import kotlinx.coroutines.launch

class ConfiguracionActivity : AppCompatActivity() {

    private var cargando = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_configuracion)

        val swOscuro = findViewById<Switch>(R.id.swModoOscuro)
        val swNotificaciones = findViewById<Switch>(R.id.swNotificaciones)
        val spIdioma = findViewById<Spinner>(R.id.spIdioma)
        val idiomas = listOf("Español", "Inglés")

        val adaptador = object : ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, idiomas
        ) {
            override fun getView(pos: Int, vista: View?, padre: ViewGroup): View =
                super.getView(pos, vista, padre).apply {
                    (this as TextView).setTextColor(Color.parseColor("#382B46"))
                    setBackgroundColor(Color.WHITE)
                }

            override fun getDropDownView(pos: Int, vista: View?, padre: ViewGroup): View =
                super.getDropDownView(pos, vista, padre).apply {
                    (this as TextView).setTextColor(Color.parseColor("#382B46"))
                    setBackgroundColor(Color.WHITE)
                }
        }

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spIdioma.adapter = adaptador

        lifecycleScope.launch {
            val oscuro = Preferencias.obtenerModoOscuro(this@ConfiguracionActivity)
            val notificaciones = Preferencias.obtenerNotificaciones(this@ConfiguracionActivity)
            val idioma = Preferencias.obtenerIdioma(this@ConfiguracionActivity)

            swOscuro.setOnCheckedChangeListener(null)
            swNotificaciones.setOnCheckedChangeListener(null)

            swOscuro.isChecked = oscuro
            swNotificaciones.isChecked = notificaciones
            spIdioma.setSelection(idiomas.indexOf(idioma).coerceAtLeast(0))

            cargando = false

            swOscuro.setOnCheckedChangeListener { _, valor ->
                lifecycleScope.launch {
                    Preferencias.guardarModoOscuro(this@ConfiguracionActivity, valor)
                }

                AppCompatDelegate.setDefaultNightMode(
                    if (valor) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
            }

            swNotificaciones.setOnCheckedChangeListener { _, valor ->
                lifecycleScope.launch {
                    Preferencias.guardarNotificaciones(this@ConfiguracionActivity, valor)
                }
            }
        }

        spIdioma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!cargando) lifecycleScope.launch {
                    Preferencias.guardarIdioma(this@ConfiguracionActivity, idiomas[position])
                }
            }
        }

        findViewById<Button>(R.id.btnRestablecer).setOnClickListener {
            lifecycleScope.launch {
                cargando = true
                Preferencias.restablecer(this@ConfiguracionActivity)

                swOscuro.isChecked = false
                swNotificaciones.isChecked = true
                spIdioma.setSelection(0)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                cargando = false
                Toast.makeText(this@ConfiguracionActivity, "Configuración restablecida", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnVolver).setOnClickListener { finish() }
    }
}