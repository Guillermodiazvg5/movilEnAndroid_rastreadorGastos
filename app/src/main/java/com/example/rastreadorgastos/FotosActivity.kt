package com.example.rastreadorgastos

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

class FotosActivity : AppCompatActivity() {

    private lateinit var adapter: FotoGroupAdapter
    private lateinit var listaFotos: MutableList<Foto>
    private lateinit var etiquetasGuardadas: MutableList<String>
    private lateinit var recycler: RecyclerView
    private lateinit var tvSinFotos: TextView
    private lateinit var layoutBottomBar: LinearLayout
    private lateinit var tvSeleccionadasCount: TextView
    private lateinit var fabAgregarEtiqueta: FloatingActionButton
    private lateinit var fabEditarTitulo: FloatingActionButton
    private lateinit var fabAgregarFoto: FloatingActionButton
    private lateinit var btnBorrarSeleccionadas: ImageButton

    private val lanzadorGaleria = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        if (resultado.resultCode == RESULT_OK) {
            val uri = resultado.data?.data
            if (uri != null) {
                try {
                    contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } catch (_: Exception) {}

                val nombreArchivo = obtenerNombreArchivo(uri)
                val nuevaFoto = Foto(
                    id = UUID.randomUUID().toString(),
                    titulo = nombreArchivo,
                    descripcion = "Sin descripción",
                    etiqueta = "sin etiqueta",
                    imageUri = uri.toString()
                )

                listaFotos.add(nuevaFoto)
                FotoStorage.guardar(this, listaFotos)
                actualizarAdapter()
                Toast.makeText(this, "Foto cargada: $nombreArchivo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos)

        recycler = findViewById(R.id.recyclerFotos)
        tvSinFotos = findViewById(R.id.tvSinFotos)
        layoutBottomBar = findViewById(R.id.layoutBottomBar)
        tvSeleccionadasCount = findViewById(R.id.tvSeleccionadasCount)
        fabAgregarEtiqueta = findViewById(R.id.fabAgregarEtiqueta)
        fabEditarTitulo = findViewById(R.id.fabEditarTitulo)
        fabAgregarFoto = findViewById(R.id.fabAgregarFoto)
        btnBorrarSeleccionadas = findViewById(R.id.btnBorrarSeleccionadas)

        listaFotos = FotoStorage.cargar(this)
        etiquetasGuardadas = EtiquetaStorage.cargar(this)

        for (foto in listaFotos) {
            if (foto.etiqueta != "sin etiqueta" && !etiquetasGuardadas.contains(foto.etiqueta)) {
                etiquetasGuardadas.add(foto.etiqueta)
            }
        }
        EtiquetaStorage.guardar(this, etiquetasGuardadas)

        actualizarAdapter()

        fabAgregarFoto.setOnClickListener {
            abrirGaleria()
        }

        fabAgregarEtiqueta.setOnClickListener {
            mostrarDialogoGestionEtiquetas()
        }

        fabEditarTitulo.setOnClickListener {
            val fotoSeleccionada = listaFotos.firstOrNull { it.isSelected }
            if (fotoSeleccionada != null) {
                mostrarDialogoEditarTitulo(fotoSeleccionada)
            }
        }

        btnBorrarSeleccionadas.setOnClickListener {
            confirmarBorrarSeleccionadas()
        }

        setupSidebar()
    }

    private fun obtenerNombreArchivo(uri: Uri): String {
        var nombre: String? = null
        if (uri.scheme == "content") {
            try {
                contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (index != -1) {
                            nombre = cursor.getString(index)
                        }
                    }
                }
            } catch (_: Exception) {}
        }
        if (nombre == null) {
            nombre = uri.path
            val cut = nombre?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                nombre = nombre.substring(cut + 1)
            }
        }
        return nombre ?: "Nueva Foto"
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        try {
            lanzadorGaleria.launch(intent)
        } catch (_: Exception) {
            val intentPick = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            lanzadorGaleria.launch(intentPick)
        }
    }

    private fun mostrarDialogoEditarTitulo(foto: Foto) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Modificar Título de la Foto")

        val input = EditText(this).apply {
            setText(foto.titulo)
            setSelection(foto.titulo.length)
        }
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
            addView(input)
        }
        builder.setView(container)

        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoTitulo = input.text.toString().trim()
            if (nuevoTitulo.isNotEmpty()) {
                foto.titulo = nuevoTitulo
                foto.isSelected = false
                FotoStorage.guardar(this, listaFotos)
                actualizarAdapter()
                Toast.makeText(this, "Título actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun mostrarDialogoGestionEtiquetas() {
        val seleccionadas = listaFotos.filter { it.isSelected }
        if (seleccionadas.isEmpty()) return

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Menú Agregar Etiqueta")

        val opcionesItems = mutableListOf<String>()
        opcionesItems.add("➕ Crear una nueva etiqueta")
        val etiquetasOrdenadas = etiquetasGuardadas.sorted()
        for (tag in etiquetasOrdenadas) {
            opcionesItems.add("🏷️ $tag")
        }

        builder.setItems(opcionesItems.toTypedArray()) { _, which ->
            if (which == 0) {
                mostrarDialogoCrearNuevaEtiqueta()
            } else {
                val etiquetaSeleccionada = etiquetasOrdenadas[which - 1]
                mostrarOpcionesDeEtiqueta(etiquetaSeleccionada)
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun mostrarDialogoCrearNuevaEtiqueta() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Crear Nueva Etiqueta")

        val input = EditText(this).apply {
            hint = "Nombre de la etiqueta..."
        }
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
            addView(input)
        }
        builder.setView(container)

        builder.setPositiveButton("Crear y Aplicar") { _, _ ->
            val nueva = input.text.toString().trim().lowercase()
            if (nueva.isNotEmpty()) {
                val etiquetaRepetida = etiquetasGuardadas.any { it.equals(nueva, ignoreCase = true) }
                if (etiquetaRepetida) {
                    Toast.makeText(this, "La etiqueta '$nueva' ya existe. No se puede repetir.", Toast.LENGTH_LONG).show()
                } else {
                    etiquetasGuardadas.add(nueva)
                    EtiquetaStorage.guardar(this, etiquetasGuardadas)
                    aplicarEtiquetaASeleccionadas(nueva)
                }
            } else {
                Toast.makeText(this, "Ingresa un nombre válido", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun mostrarOpcionesDeEtiqueta(etiqueta: String) {
        val opcionesTag = arrayOf("✔️ Aplicar a fotos seleccionadas", "✏️ Modificar nombre", "🗑️ Borrar etiqueta")
        AlertDialog.Builder(this)
            .setTitle("Opciones para '$etiqueta'")
            .setItems(opcionesTag) { _, action ->
                when (action) {
                    0 -> aplicarEtiquetaASeleccionadas(etiqueta)
                    1 -> mostrarDialogoModificarEtiqueta(etiqueta)
                    2 -> confirmarBorrarEtiqueta(etiqueta)
                }
            }
            .setNegativeButton("Volver", null)
            .show()
    }

    private fun mostrarDialogoModificarEtiqueta(etiquetaAntigua: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Modificar Etiqueta: $etiquetaAntigua")

        val input = EditText(this).apply {
            setText(etiquetaAntigua)
            setSelection(etiquetaAntigua.length)
        }
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 10)
            addView(input)
        }
        builder.setView(container)

        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevaEtiqueta = input.text.toString().trim().lowercase()
            if (nuevaEtiqueta.isNotEmpty() && nuevaEtiqueta != etiquetaAntigua) {
                val etiquetaRepetida = etiquetasGuardadas.any { it.equals(nuevaEtiqueta, ignoreCase = true) }
                if (etiquetaRepetida) {
                    Toast.makeText(this, "La etiqueta '$nuevaEtiqueta' ya existe.", Toast.LENGTH_SHORT).show()
                } else {
                    val index = etiquetasGuardadas.indexOfFirst { it.equals(etiquetaAntigua, ignoreCase = true) }
                    if (index != -1) {
                        etiquetasGuardadas[index] = nuevaEtiqueta
                    }
                    for (foto in listaFotos) {
                        if (foto.etiqueta.equals(etiquetaAntigua, ignoreCase = true)) {
                            foto.etiqueta = nuevaEtiqueta
                        }
                    }
                    EtiquetaStorage.guardar(this, etiquetasGuardadas)
                    FotoStorage.guardar(this, listaFotos)
                    actualizarAdapter()
                    Toast.makeText(this, "Etiqueta modificada a '$nuevaEtiqueta'", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun confirmarBorrarEtiqueta(etiqueta: String) {
        AlertDialog.Builder(this)
            .setTitle("Borrar Etiqueta")
            .setMessage("¿Estás seguro de borrar la etiqueta '$etiqueta'? Las fotos asociadas pasarán a estar 'sin etiqueta'.")
            .setPositiveButton("Sí, borrar") { _, _ ->
                etiquetasGuardadas.remove(etiqueta)
                for (foto in listaFotos) {
                    if (foto.etiqueta.equals(etiqueta, ignoreCase = true)) {
                        foto.etiqueta = "sin etiqueta"
                    }
                }
                EtiquetaStorage.guardar(this, etiquetasGuardadas)
                FotoStorage.guardar(this, listaFotos)
                actualizarAdapter()
                Toast.makeText(this, "Etiqueta '$etiqueta' borrada", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun aplicarEtiquetaASeleccionadas(etiqueta: String) {
        if (!etiquetasGuardadas.contains(etiqueta)) {
            etiquetasGuardadas.add(etiqueta)
            EtiquetaStorage.guardar(this, etiquetasGuardadas)
        }
        for (foto in listaFotos) {
            if (foto.isSelected) {
                foto.etiqueta = etiqueta
                foto.isSelected = false
            }
        }
        FotoStorage.guardar(this, listaFotos)
        actualizarAdapter()
        Toast.makeText(this, "¡Fotos etiquetadas como '$etiqueta'!", Toast.LENGTH_SHORT).show()
    }

    private fun confirmarBorrarSeleccionadas() {
        val seleccionadas = listaFotos.filter { it.isSelected }
        if (seleccionadas.isEmpty()) return

        AlertDialog.Builder(this)
            .setTitle("Eliminar fotos")
            .setMessage("¿Deseas eliminar las ${seleccionadas.size} foto(s) seleccionada(s)?")
            .setPositiveButton("Sí, eliminar") { _, _ ->
                listaFotos.removeAll(seleccionadas)
                FotoStorage.guardar(this, listaFotos)
                actualizarAdapter()
                Toast.makeText(this, "Fotos eliminadas", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun actualizarAdapter() {
        val itemsAgrupados = mutableListOf<GalleryItem>()

        // Galería: agrupa únicamente las etiquetas que tienen fotos actualmente (comportamiento original)
        val agrupados = listaFotos.groupBy { it.etiqueta }
        for ((etiqueta, fotosDeGrupo) in agrupados) {
            itemsAgrupados.add(GalleryItem.Header(etiqueta))
            for (foto in fotosDeGrupo) {
                itemsAgrupados.add(GalleryItem.PhotoItem(foto))
            }
        }

        val isAnySelected = listaFotos.any { it.isSelected }

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = FotoGroupAdapter(itemsAgrupados, isAnySelected, { foto ->
            mostrarFotoCompleta(foto)
        }, {
            actualizarAdapter()
        }, {
            actualizarAdapter()
        })
        recycler.adapter = adapter

        verificarSeleccion()

        if (listaFotos.isEmpty()) {
            recycler.visibility = View.GONE
            tvSinFotos.visibility = View.VISIBLE
        } else {
            recycler.visibility = View.VISIBLE
            tvSinFotos.visibility = View.GONE
        }
    }

    private fun verificarSeleccion() {
        val seleccionadasCount = listaFotos.count { it.isSelected }
        if (seleccionadasCount > 0) {
            layoutBottomBar.visibility = View.VISIBLE
            fabAgregarEtiqueta.visibility = View.VISIBLE
            fabAgregarFoto.visibility = View.GONE
            tvSeleccionadasCount.text = "$seleccionadasCount seleccionada(s)"

            if (seleccionadasCount == 1) {
                fabEditarTitulo.visibility = View.VISIBLE
            } else {
                fabEditarTitulo.visibility = View.GONE
            }
        } else {
            layoutBottomBar.visibility = View.GONE
            fabAgregarEtiqueta.visibility = View.GONE
            fabEditarTitulo.visibility = View.GONE
            fabAgregarFoto.visibility = View.VISIBLE
        }
    }

    private fun mostrarFotoCompleta(foto: Foto) {
        val builder = AlertDialog.Builder(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                700
            )
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        if (foto.imageUri.isNotEmpty()) {
            Glide.with(this).load(Uri.parse(foto.imageUri)).into(imageView)
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        val tvInfo = TextView(this).apply {
            text = "🏷️ Etiqueta: ${foto.etiqueta}\n\n📖 Descripción:\n${foto.descripcion}"
            textSize = 14f
            setPadding(0, 20, 0, 0)
        }

        layout.addView(imageView)
        layout.addView(tvInfo)

        builder.setTitle(foto.titulo)
            .setView(layout)
            .setPositiveButton("Cerrar", null)
            .setNeutralButton("🗑️ Eliminar foto") { _, _ ->
                confirmarEliminarFoto(foto)
            }
            .show()
    }

    private fun confirmarEliminarFoto(foto: Foto) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar foto")
            .setMessage("¿Seguro que deseas eliminar la foto \"${foto.titulo}\"?")
            .setPositiveButton("Sí, eliminar") { _, _ ->
                listaFotos.remove(foto)
                FotoStorage.guardar(this, listaFotos)
                actualizarAdapter()
                Toast.makeText(this, "Foto eliminada", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun setupSidebar() {
        findViewById<LinearLayout>(R.id.menuPerfil).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.menuFotos).setOnClickListener {
            Toast.makeText(this, "Ya estás en Fotos", Toast.LENGTH_SHORT).show( )
        }

        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.menuBotones).setOnClickListener {
            val intent = Intent(this, AccionesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.menuIngresos).setOnClickListener {
            val intent = Intent(this, MiPresupuestoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
