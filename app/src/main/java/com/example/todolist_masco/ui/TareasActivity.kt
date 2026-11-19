package com.example.todolist_masco.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todolist_masco.R
import com.example.todolist_masco.core.SessionManager
import com.example.todolist_masco.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TareasActivity : AppCompatActivity() {

    private val dao by lazy { AppDatabase.getInstance(this).todoDao() }
    private var usuarioId = 0L

    private var filtro = "Todas"
    private var pendientes = false
    private var busqueda = ""
    private var orden = "Más recientes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filtro = intent.getStringExtra("filtro") ?: "Todas"
        pendientes = intent.getBooleanExtra("pendientes", false)

        lifecycleScope.launch {
            usuarioId = SessionManager(this@TareasActivity).userId.first()

            if (usuarioId == 0L) {
                finish()
                return@launch
            }

            mostrarTareas()
        }
    }

    override fun onResume() {
        super.onResume()
        if (usuarioId != 0L) actualizarLista()
    }

    private fun mostrarTareas() {
        setContentView(R.layout.pantalla_tareas)

        findViewById<TextView>(R.id.titulo).text =
            if (pendientes) "Tareas pendientes" else "Mis tareas"

        val categorias = listOf("Todas") + dao.categorias(usuarioId).map { it.nombre }
        if (filtro !in categorias) filtro = "Todas"

        ponerOpciones(findViewById(R.id.spFiltro), categorias, filtro) {
            filtro = it
            actualizarLista()
        }

        val buscar = findViewById<EditText>(R.id.txtBuscar)
        buscar.setText(busqueda)

        buscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                busqueda = s.toString()
                actualizarLista()
            }
        })

        val ordenes = listOf(
            "Más recientes", "Nombre A-Z", "Nombre Z-A",
            "Prioritarias", "Pendientes", "Completadas"
        )

        ponerOpciones(findViewById(R.id.spOrden), ordenes, orden) {
            orden = it
            actualizarLista()
        }

        findViewById<Button>(R.id.btnAgregar).setOnClickListener { editarTarea() }

        configurarBarra()
        actualizarLista()
    }

    private fun actualizarLista() {
        val lista = findViewById<LinearLayout>(R.id.listaTareas) ?: return
        val categorias = dao.categorias(usuarioId)

        var visibles = dao.tareas(usuarioId).filter {
            (!pendientes || it.estado != "Completada") &&
                    (filtro == "Todas" || it.categoria == filtro) &&
                    it.nombre.contains(busqueda, ignoreCase = true)
        }

        visibles = when (orden) {
            "Nombre A-Z" -> visibles.sortedBy { it.nombre.lowercase() }
            "Nombre Z-A" -> visibles.sortedByDescending { it.nombre.lowercase() }
            "Prioritarias" -> visibles.sortedByDescending { it.prioridad }
            "Pendientes" -> visibles.sortedBy { it.estado == "Completada" }
            "Completadas" -> visibles.sortedByDescending { it.estado == "Completada" }
            else -> visibles.sortedByDescending { it.id }
        }

        findViewById<TextView>(R.id.resumen).text =
            "${visibles.size} tareas · ${visibles.count { it.estado != "Completada" }} pendientes"

        lista.removeAllViews()

        visibles.forEach { tarea ->
            val vista = layoutInflater.inflate(R.layout.item_tarea, lista, false)
            val nombre = vista.findViewById<TextView>(R.id.lblNombre)
            val check = vista.findViewById<CheckBox>(R.id.chkHecha)

            nombre.text = tarea.nombre

            val lblCategoria = vista.findViewById<TextView>(R.id.lblCategoria)
            val categoria = categorias.find { it.nombre == tarea.categoria }

            lblCategoria.text = "●  ${tarea.categoria} · ${tarea.estado}"
            lblCategoria.setTextColor(
                Color.parseColor(categoria?.color ?: "#7048AB")
            )

            if (tarea.estado == "Completada")
                nombre.paintFlags = nombre.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            check.isChecked = tarea.estado == "Completada"

            check.setOnCheckedChangeListener { _, marcado ->
                dao.actualizarTarea(
                    tarea.copy(
                        completada = marcado,
                        estado = if (marcado) "Completada" else "Pendiente"
                    )
                )
                actualizarLista()
            }

            vista.findViewById<TextView>(R.id.btnPrioridad).apply {
                alpha = if (tarea.prioridad) 1f else 0.35f

                setOnClickListener {
                    dao.actualizarTarea(tarea.copy(prioridad = !tarea.prioridad))
                    actualizarLista()
                }
            }

            vista.findViewById<TextView>(R.id.btnEditarTarea)
                .setOnClickListener { editarTarea(tarea) }

            lista.addView(vista)
        }
    }

    private fun editarTarea(tarea: Tarea? = null) {
        val vista = layoutInflater.inflate(R.layout.dialogo_tarea, null)
        val dialogo = AlertDialog.Builder(this).setView(vista).create()

        val nombre = vista.findViewById<EditText>(R.id.txtNombre)
        val descripcion = vista.findViewById<EditText>(R.id.txtDescripcion)
        val prioridad = vista.findViewById<CheckBox>(R.id.chkPrioridad)
        val spCategoria = vista.findViewById<Spinner>(R.id.spCategoria)
        val spEstado = vista.findViewById<Spinner>(R.id.spEstado)
        val eliminar = vista.findViewById<Button>(R.id.btnEliminar)

        val categorias = dao.categorias(usuarioId).map { it.nombre }

        if (categorias.isEmpty()) {
            aviso("Primero crea una categoría")
            return
        }

        ponerOpciones(spCategoria, categorias, tarea?.categoria)

        ponerOpciones(
            spEstado,
            listOf("Pendiente", "En progreso", "Completada"),
            tarea?.estado ?: "Pendiente"
        )

        if (tarea != null) {
            vista.findViewById<TextView>(R.id.titulo).text = "Editar tarea"
            nombre.setText(tarea.nombre)
            descripcion.setText(tarea.descripcion)
            prioridad.isChecked = tarea.prioridad
        } else {
            eliminar.visibility = View.GONE
        }

        vista.findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            val texto = nombre.text.toString().trim()

            if (texto.isEmpty()) {
                aviso("Escribe el nombre de la tarea")
                return@setOnClickListener
            }

            val estado = spEstado.selectedItem.toString()

            val nueva = (tarea ?: Tarea(usuarioId = usuarioId, nombre = texto)).copy(
                nombre = texto,
                descripcion = descripcion.text.toString(),
                categoria = spCategoria.selectedItem.toString(),
                prioridad = prioridad.isChecked,
                estado = estado,
                completada = estado == "Completada"
            )

            if (tarea == null) dao.insertarTarea(nueva)
            else dao.actualizarTarea(nueva)

            dialogo.dismiss()
            actualizarLista()
        }

        eliminar.setOnClickListener {
            if (tarea != null) {
                AlertDialog.Builder(this)
                    .setMessage("¿Eliminar esta tarea?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        dao.eliminarTarea(tarea)
                        dialogo.dismiss()
                        actualizarLista()
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            }
        }

        vista.findViewById<Button>(R.id.btnCancelar)
            .setOnClickListener { dialogo.dismiss() }

        dialogo.show()
    }

    private fun ponerOpciones(
        spinner: Spinner,
        opciones: List<String>,
        seleccionado: String? = null,
        alCambiar: ((String) -> Unit)? = null
    ) {
        val adaptador = object : ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, opciones
        ) {
            override fun getView(pos: Int, vista: View?, padre: android.view.ViewGroup): View =
                super.getView(pos, vista, padre).apply {
                    (this as TextView).setTextColor(Color.parseColor("#382B46"))
                }

            override fun getDropDownView(pos: Int, vista: View?, padre: android.view.ViewGroup): View =
                super.getDropDownView(pos, vista, padre).apply {
                    (this as TextView).setTextColor(Color.parseColor("#382B46"))
                }
        }

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador
        spinner.setSelection(opciones.indexOf(seleccionado).coerceAtLeast(0))

        if (alCambiar != null) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    posicion: Int,
                    id: Long
                ) {
                    if (opciones[posicion] != seleccionado)
                        alCambiar(opciones[posicion])
                }
            }
        }
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
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
            )

            boton.setOnClickListener {
                when (i) {
                    0 -> {
                        pendientes = false
                        filtro = "Todas"
                        busqueda = ""
                        mostrarTareas()
                    }

                    1 -> {
                        pendientes = true
                        filtro = "Todas"
                        busqueda = ""
                        mostrarTareas()
                    }

                    2 -> {
                        startActivity(Intent(this, CategoriasActivity::class.java))
                        finish()
                    }

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