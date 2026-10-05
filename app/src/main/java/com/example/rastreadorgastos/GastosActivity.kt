package com.example.rastreadorgastos

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GastosActivity : AppCompatActivity() {

    private lateinit var adapter: GastoAdapter
    private lateinit var listaGastos: MutableList<Gasto>
    private lateinit var tvListaVacia: TextView
    private lateinit var recycler: RecyclerView

    private val lanzadorAgregarGasto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        if (resultado.resultCode == Activity.RESULT_OK) {
            val data = resultado.data
            val emoji = data?.getStringExtra("emoji") ?: "💸"
            val nombre = data?.getStringExtra("nombre") ?: ""
            val categoria = data?.getStringExtra("categoria") ?: ""
            val descripcion = data?.getStringExtra("descripcion") ?: ""
            val monto = data?.getStringExtra("monto") ?: ""

            // Generar fecha y hora actual
            val fechaHora = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

            listaGastos.add(0, Gasto(emoji, nombre, categoria, descripcion, "-$$monto", fechaHora))
            adapter.notifyItemInserted(0)

            GastoStorage.guardar(this, listaGastos)
            actualizarTotal()
            actualizarMensajeVacio()

            Toast.makeText(this, "Gasto agregado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos)

        listaGastos = GastoStorage.cargar(this)

        recycler = findViewById(R.id.recyclerGastos)
        tvListaVacia = findViewById(R.id.tvListaVacia)

        recycler.layoutManager = LinearLayoutManager(this)
        adapter = GastoAdapter(listaGastos) { posicion ->
            confirmarEliminar(posicion)
        }
        recycler.adapter = adapter

        actualizarTotal()
        actualizarMensajeVacio()

        val fab = findViewById<FloatingActionButton>(R.id.fabAgregar)
        fab.setOnClickListener {
            val intent = Intent(this, AgregarGastoActivity::class.java)
            lanzadorAgregarGasto.launch(intent)
        }

        // ===== Barra lateral =====
        findViewById<LinearLayout>(R.id.menuPerfil).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.menuFotos).setOnClickListener {
            Toast.makeText(this, "Fotos - Próximamente", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            Toast.makeText(this, "Video - Próximamente", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.menuBotones).setOnClickListener {
            startActivity(Intent(this, AccionesActivity::class.java))
        }
    }

    // Doble confirmación para eliminar
    private fun confirmarEliminar(posicion: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar gasto")
            .setMessage("¿Seguro que quieres eliminar \"${listaGastos[posicion].nombre}\"?")
            .setPositiveButton("Sí") { _, _ ->
                listaGastos.removeAt(posicion)
                adapter.notifyItemRemoved(posicion)
                GastoStorage.guardar(this, listaGastos)
                actualizarTotal()
                actualizarMensajeVacio()
                Toast.makeText(this, "Gasto eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun actualizarTotal() {
        var total = 0
        for (gasto in listaGastos) {
            val numero = gasto.monto
                .replace("-$", "")
                .replace(".", "")
                .replace(",", "")
                .trim()
            total += numero.toIntOrNull() ?: 0
        }
        val formateado = String.format("%,d", total).replace(",", ".")
        findViewById<TextView>(R.id.totalGastos).text = "Total gastos este mes: \$$formateado"
    }

    private fun actualizarMensajeVacio() {
        if (listaGastos.isEmpty()) {
            recycler.visibility = View.GONE
            tvListaVacia.visibility = View.VISIBLE
        } else {
            recycler.visibility = View.VISIBLE
            tvListaVacia.visibility = View.GONE
        }
    }
}