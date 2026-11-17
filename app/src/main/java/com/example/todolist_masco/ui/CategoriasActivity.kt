package com.example.todolist_masco.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist_masco.R
import com.example.todolist_masco.data.*

class CategoriasActivity : AppCompatActivity() {

    private lateinit var dao: TodoDao
    private var usuarioId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = AppDatabase.getInstance(this).todoDao()
        usuarioId = getSharedPreferences("sesion_todolist", MODE_PRIVATE).getLong("usuario", 0L)

        if (usuarioId == 0L) {
            finish()
            return
        }

        mostrarCategorias()
    }

    override fun onResume() {
        super.onResume()
        if (usuarioId != 0L) mostrarCategorias()
    }

    private fun mostrarCategorias() {
        setContentView(R.layout.pantalla_categorias)

        val categorias = dao.categorias(usuarioId)
        val tareas = dao.tareas(usuarioId)
        val contenedor = findViewById<LinearLayout>(R.id.contenedorCategorias)

        findViewById<TextView>(R.id.lblResumen).text =
            "${categorias.size} categorías · ${tareas.size} tareas"

        contenedor.removeAllViews()

        for (categoria in categorias) {
            val total = tareas.count { it.categoria == categoria.nombre }
            val fila = layoutInflater.inflate(R.layout.item_categoria, contenedor, false)

            fila.findViewById<View>(R.id.barraColor)
                .setBackgroundColor(Color.parseColor(categoria.color))

            fila.findViewById<TextView>(R.id.lblNombreCategoria).text = categoria.nombre
            fila.findViewById<TextView>(R.id.lblCantidadTareas).text =
                if (total == 1) "1 tarea" else "$total tareas"

            fila.findViewById<LinearLayout>(R.id.infoCategoria).setOnClickListener {
                abrirCategoria(categoria.nombre)
            }

            fila.findViewById<TextView>(R.id.btnAbrirCategoria).setOnClickListener {
                abrirCategoria(categoria.nombre)
            }

            fila.findViewById<TextView>(R.id.btnEliminarCategoria).setOnClickListener {
                if (total > 0) {
                    aviso("Primero elimina las tareas de esta categoría")
                } else {
                    AlertDialog.Builder(this)
                        .setMessage("¿Eliminar ${categoria.nombre}?")
                        .setPositiveButton("Eliminar") { _, _ ->
                            dao.eliminarCategoria(categoria.id, usuarioId)
                            mostrarCategorias()
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                }
            }

            contenedor.addView(fila)
        }

        findViewById<Button>(R.id.btnNuevaCategoria).setOnClickListener { nuevaCategoria() }
        configurarBarra()
    }

    private fun abrirCategoria(nombre: String) {
        val intent = Intent(this, TareasActivity::class.java)
        intent.putExtra("filtro", nombre)
        startActivity(intent)
        finish()
    }

    private fun nuevaCategoria() {
        val vista = layoutInflater.inflate(R.layout.dialogo_categoria, null)
        val dialogo = AlertDialog.Builder(this).setView(vista).create()
        val txtNombre = vista.findViewById<EditText>(R.id.txtNombre)
        val spColor = vista.findViewById<Spinner>(R.id.spColor)

        val colores = listOf("Morado", "Morado oscuro", "Lila", "Crema")
        val codigos = listOf("#7048AB", "#4B2B83", "#B99CDD", "#E8D9C4")

        val opciones = object : ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, colores
        ) {
            override fun getView(pos: Int, vista: View?, padre: ViewGroup): View {
                return super.getView(pos, vista, padre).apply {
                    (this as TextView).setTextColor(Color.parseColor("#382B46"))
                }
            }

            override fun getDropDownView(pos: Int, vista: View?, padre: ViewGroup): View {
                return super.getDropDownView(pos, vista, padre).apply {
                    (this as TextView).setTextColor(Color.parseColor("#382B46"))
                }
            }
        }

        opciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spColor.adapter = opciones

        vista.findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val nombre = txtNombre.text.toString().trim()

            when {
                nombre.isBlank() -> txtNombre.error = "Escribe el nombre de la categoría"
                nombre.length < 3 -> txtNombre.error = "Mínimo 3 caracteres"
                dao.categorias(usuarioId).any { it.nombre.equals(nombre, true) } ->
                    txtNombre.error = "Esa categoría ya existe"
                else -> {
                    dao.insertarCategoria(
                        Categoria(
                            usuarioId = usuarioId,
                            nombre = nombre,
                            color = codigos[spColor.selectedItemPosition]
                        )
                    )
                    dialogo.dismiss()
                    mostrarCategorias()
                }
            }
        }

        vista.findViewById<Button>(R.id.btnCancelar).setOnClickListener { dialogo.dismiss() }
        dialogo.show()
    }

    private fun configurarBarra() {
        val barra = findViewById<LinearLayout>(R.id.barraNavegacion)
        barra.removeAllViews()

        val opciones = listOf("Tareas", "Pendientes", "Categorías", "Perfil")

        for (i in opciones.indices) {
            val boton = TextView(this).apply {
                text = opciones[i]
                textSize = 12f
                gravity = Gravity.CENTER
                setTextColor(Color.parseColor("#7048AB"))
            }

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
                    2 -> {}
                    3 -> {
                        startActivity(Intent(this, PerfilActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun aviso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}