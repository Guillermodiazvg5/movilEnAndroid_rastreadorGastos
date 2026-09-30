package com.example.rastreadorgastos

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var themeSwitch: Switch
    private val PREFS_NAME = "ElPesitoPrefs"
    private val KEY_DARK_MODE = "dark_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ===== Configuración del Switch de tema =====
        themeSwitch = findViewById(R.id.themeSwitch)
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(KEY_DARK_MODE, false)
        themeSwitch.isChecked = isDarkMode

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // ===== Configuración de la barra lateral =====
        val menuPerfil = findViewById<LinearLayout>(R.id.menuPerfil)
        val menuGastos = findViewById<LinearLayout>(R.id.menuGastos)
        val menuFotos = findViewById<LinearLayout>(R.id.menuFotos)
        val menuVideo = findViewById<LinearLayout>(R.id.menuVideo)
        val menuWeb = findViewById<LinearLayout>(R.id.menuWeb)
        val menuBotones = findViewById<LinearLayout>(R.id.menuBotones)

        menuPerfil.setOnClickListener {
            // Ya estamos en Perfil, no hacemos nada
            Toast.makeText(this, "Ya estás en Perfil", Toast.LENGTH_SHORT).show()
        }

        menuGastos.setOnClickListener {
            val intent = Intent(this, GastosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        menuFotos.setOnClickListener {
            Toast.makeText(this, "Fotos - Próximamente", Toast.LENGTH_SHORT).show()
        }

        menuVideo.setOnClickListener {
            Toast.makeText(this, "Video - Próximamente", Toast.LENGTH_SHORT).show()
        }

        menuWeb.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Botones → abre AccionesActivity
        menuBotones.setOnClickListener {
            val intent = Intent(this, AccionesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
