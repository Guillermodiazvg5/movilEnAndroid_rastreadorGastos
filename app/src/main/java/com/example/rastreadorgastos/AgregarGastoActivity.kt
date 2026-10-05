package com.example.rastreadorgastos

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgregarGastoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_gasto)

        // === Spinner de categorías ===
        val spinner = findViewById<Spinner>(R.id.spinnerCategoria)
        val categorias = arrayOf("Comida", "Transporte", "Hogar", "Salud", "Ocio", "Otros")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // === Botón Guardar ===
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombre).text.toString().trim()
            val monto = findViewById<EditText>(R.id.etMonto).text.toString().trim()
            val descripcion = findViewById<EditText>(R.id.etDescripcion).text.toString().trim()
            val categoria = spinner.selectedItem.toString()

            if (nombre.isEmpty() || monto.isEmpty()) {
                Toast.makeText(this, "Por favor completa Nombre y Monto", Toast.LENGTH_SHORT).show()
            } else {
                val intentResultado = Intent()
                intentResultado.putExtra("emoji", emojiParaCategoria(categoria))
                intentResultado.putExtra("nombre", nombre)
                intentResultado.putExtra("categoria", categoria)
                intentResultado.putExtra("descripcion", descripcion)
                intentResultado.putExtra("monto", monto)
                setResult(RESULT_OK, intentResultado)
                finish()
            }
        }

        // === Barra lateral ===
        findViewById<LinearLayout>(R.id.menuPerfil).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }



        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }

        // ✅ Botón "Web" → abre WebActivity
        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ✅ Botón "Gastos" (menuBotones) → abre AccionesActivity
        findViewById<LinearLayout>(R.id.menuBotones).setOnClickListener {
            startActivity(Intent(this, AccionesActivity::class.java))
        }
    }

    private fun emojiParaCategoria(categoria: String): String {
        return when (categoria) {
            "Comida" -> "🍔"
            "Transporte" -> "🚗"
            "Hogar" -> "🛒"
            "Salud" -> "💊"
            "Ocio" -> "🎮"
            else -> "💸"
        }
    }
}