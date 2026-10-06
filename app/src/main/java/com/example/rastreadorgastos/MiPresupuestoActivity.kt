package com.example.rastreadorgastos

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.NumberFormat
import java.util.Locale

class MiPresupuestoActivity : AppCompatActivity() {

    private lateinit var adapter: IngresoAdapter
    private lateinit var lista: MutableList<Ingreso>
    private lateinit var pieChart: PieChart
    private lateinit var tvTotal: TextView
    private lateinit var tvSinIngresos: TextView
    private lateinit var tvTituloIngresos: TextView
    private lateinit var recycler: RecyclerView

    private val CODIGO_AGREGAR = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_presupuesto)

        // Referencias
        pieChart = findViewById(R.id.pieChart)
        tvTotal = findViewById(R.id.totalPresupuesto)
        tvSinIngresos = findViewById(R.id.tvSinIngresos)
        tvTituloIngresos = findViewById(R.id.tvTituloIngresos)
        recycler = findViewById(R.id.recyclerIngresos)

        // Cargar ingresos desde el archivo
        lista = IngresoStorage.cargar(this)

        // RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = IngresoAdapter(lista) { posicion ->
            confirmarEliminar(posicion)
        }
        recycler.adapter = adapter

        // Actualizar total, gráfico y visibilidad
        actualizarTodo()

        // Botón flotante (+)
        findViewById<FloatingActionButton>(R.id.fabAgregarIngreso).setOnClickListener {
            val intent = Intent(this, DefinirPresupuestoActivity::class.java)
            startActivityForResult(intent, CODIGO_AGREGAR)
        }

        // === Barra lateral ===
        findViewById<LinearLayout>(R.id.menuPerfil).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.menuBotones).setOnClickListener {
            startActivity(Intent(this, AccionesActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.menuIngresos).setOnClickListener {
            Toast.makeText(this, "Ya estás en Ingresos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_AGREGAR && resultCode == RESULT_OK) {
            // Recargar la lista desde el archivo
            lista.clear()
            lista.addAll(IngresoStorage.cargar(this))
            adapter.notifyDataSetChanged()
            actualizarTodo()
            Toast.makeText(this, "Ingreso agregado", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Actualiza el total, el gráfico, la lista y la visibilidad
     * de los elementos según si hay ingresos o no.
     */
    private fun actualizarTodo() {
        // Calcular total
        val total = lista.sumOf { it.valor }
        val formato = NumberFormat.getNumberInstance(Locale("es", "CO"))
        tvTotal.text = "$" + formato.format(total)

        // Si la lista está vacía
        if (lista.isEmpty()) {
            pieChart.visibility = View.GONE
            tvTituloIngresos.visibility = View.GONE
            recycler.visibility = View.GONE
            tvSinIngresos.visibility = View.VISIBLE
            return
        } else {
            pieChart.visibility = View.VISIBLE
            tvTituloIngresos.visibility = View.VISIBLE
            recycler.visibility = View.VISIBLE
            tvSinIngresos.visibility = View.GONE
        }

        // Agrupar por tipo
        val agrupado = lista.groupBy { it.tipo }.mapValues { entry ->
            entry.value.sumOf { it.valor }
        }

        // Preparar datos del gráfico
        val entries = mutableListOf<PieEntry>()
        for ((tipo, valor) in agrupado) {
            entries.add(PieEntry(valor.toFloat(), tipo))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(
            Color.parseColor("#7B68EE"),
            Color.parseColor("#2ECC71"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#E91E63")
        )
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.setDrawHoleEnabled(true)
        pieChart.holeRadius = 50f
        pieChart.transparentCircleRadius = 55f
        pieChart.legend.isEnabled = true
        pieChart.setEntryLabelColor(Color.TRANSPARENT)
        pieChart.invalidate()
    }

    private fun confirmarEliminar(posicion: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar ingreso")
            .setMessage("¿Seguro que quieres eliminar \"${lista[posicion].tipo}\"?")
            .setPositiveButton("Sí") { _, _ ->
                lista.removeAt(posicion)
                IngresoStorage.guardar(this, lista)
                adapter.notifyItemRemoved(posicion)
                actualizarTodo()
                Toast.makeText(this, "Ingreso eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}