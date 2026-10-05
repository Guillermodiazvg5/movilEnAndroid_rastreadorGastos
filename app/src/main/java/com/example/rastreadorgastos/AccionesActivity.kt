package com.example.rastreadorgastos

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AccionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acciones)

        // === Botones principales ===
        findViewById<LinearLayout>(R.id.btnRegistrarGasto).setOnClickListener {
            startActivity(Intent(this, GastosActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.btnVerGrafico).setOnClickListener {
            Toast.makeText(this, "Ver Gráfico Mensual - Próximamente", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.btnDefinirPresupuesto).setOnClickListener {
            Toast.makeText(this, "Definir Presupuesto - Próximamente", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.btnEscanearFactura).setOnClickListener {
            Toast.makeText(this, "Escanear Factura - Próximamente", Toast.LENGTH_SHORT).show()
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

        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            Toast.makeText(this, "Web - Próximamente", Toast.LENGTH_SHORT).show()
        }

        // Botones → ya estamos aquí
        findViewById<LinearLayout>(R.id.menuBotones).setOnClickListener {
            Toast.makeText(this, "Ya estás en Botones", Toast.LENGTH_SHORT).show()
        }
    }
}