package com.example.rastreadorgastos

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var themeSwitch: Switch
    private lateinit var imgPerfil: ImageView
    private lateinit var emojiPerfil: TextView
    private lateinit var txtNombre: TextView
    private lateinit var txtRol: TextView
    private lateinit var txtEstudios: TextView
    private lateinit var txtExperiencia: TextView

    private val PREFS_NAME = "ElPesitoPrefs"
    private val KEY_DARK_MODE = "dark_mode"
    private val CODIGO_EDITAR_PERFIL = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === Referencias a las vistas ===
        themeSwitch = findViewById(R.id.themeSwitch)
        imgPerfil = findViewById(R.id.imgPerfil)
        emojiPerfil = findViewById(R.id.emojiPerfil)
        txtNombre = findViewById(R.id.txtNombre)
        txtRol = findViewById(R.id.txtRol)
        txtEstudios = findViewById(R.id.txtEstudios)
        txtExperiencia = findViewById(R.id.txtExperiencia)

        // === Switch de tema ===
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        themeSwitch.isChecked = prefs.getBoolean(KEY_DARK_MODE, false)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // === Botón Editar ===
        findViewById<TextView>(R.id.btnEditar).setOnClickListener {
            val intent = Intent(this, EditarPerfilActivity::class.java)
            startActivityForResult(intent, CODIGO_EDITAR_PERFIL)
        }

        // === Barra lateral ===
        findViewById<LinearLayout>(R.id.menuPerfil).setOnClickListener {
            Toast.makeText(this, "Ya estás en Perfil", Toast.LENGTH_SHORT).show()
        }


        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }

        // ✅ Botón "Web" → abre WebActivity
        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }

        // ✅ Botón "Gastos" (antes "Botones") → abre AccionesActivity
        findViewById<LinearLayout>(R.id.menuBotones).setOnClickListener {
            startActivity(Intent(this, AccionesActivity::class.java))
        }

        // === Cargar datos del perfil ===
        cargarPerfil()

        // === Animación sutil al abrir ===
        val scroll = findViewById<View>(R.id.scrollPerfil)
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fadeIn.duration = 500
        scroll.startAnimation(fadeIn)
    }

    private fun cargarPerfil() {
        val perfil = PerfilStorage.cargar(this)
        txtNombre.text = perfil.nombre
        txtRol.text = perfil.rol
        txtEstudios.text = perfil.estudios
        txtExperiencia.text = perfil.experiencia

        if (perfil.rutaFoto.isNotEmpty()) {
            val archivo = File(perfil.rutaFoto)
            if (archivo.exists()) {
                val bitmap = BitmapFactory.decodeFile(archivo.absolutePath)
                imgPerfil.setImageBitmap(bitmap)
                emojiPerfil.visibility = View.GONE
            } else {
                imgPerfil.setImageDrawable(null)
                emojiPerfil.visibility = View.VISIBLE
            }
        } else {
            imgPerfil.setImageDrawable(null)
            emojiPerfil.visibility = View.VISIBLE
        }
    }

    // Recargar el perfil cuando volvemos de editar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_EDITAR_PERFIL && resultCode == RESULT_OK) {
            cargarPerfil()
            Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
        }
    }
}