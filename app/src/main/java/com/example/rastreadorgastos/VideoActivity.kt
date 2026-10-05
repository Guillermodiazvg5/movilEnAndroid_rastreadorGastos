package com.example.rastreadorgastos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VideoActivity : AppCompatActivity() {

    private lateinit var vistaLista: View
    private lateinit var vistaReproductor: View
    private lateinit var webViewVideo: WebView
    private lateinit var tituloVideoReproduciendo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        vistaLista = findViewById(R.id.vistaLista)
        vistaReproductor = findViewById(R.id.vistaReproductor)
        webViewVideo = findViewById(R.id.webViewVideo)
        tituloVideoReproduciendo = findViewById(R.id.tituloVideoReproduciendo)

        // Configurar WebView
        webViewVideo.settings.javaScriptEnabled = true
        webViewVideo.settings.domStorageEnabled = true
        webViewVideo.webViewClient = WebViewClient()

        // === Lista de videos ===
        val listaVideos = listOf(
            Video(
                titulo = "Las 10 REGLAS CHINAS para AHORRAR y CREAR RIQUEZA",
                descripcion = "Jordi Llàtzer · 10 reglas para ahorrar y crear riqueza",
                youtubeId = "ht_T0vZ4XFU"
            ),
            Video(
                titulo = "15 Lecciones PODEROSAS de EDUCACIÓN FINANCIERA",
                descripcion = "ADN Financiero · Educación financiera y finanzas personales",
                youtubeId = "J1cWzzRNyE8"
            )
        )

        val recycler = findViewById<RecyclerView>(R.id.recyclerVideos)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = VideoAdapter(listaVideos) { video ->
            reproducirVideo(video)
        }

        // === Botón Cerrar del reproductor ===
        findViewById<TextView>(R.id.btnCerrarVideo).setOnClickListener {
            cerrarReproductor()
        }

        // === Video institucional (Próximamente) ===
        findViewById<LinearLayout>(R.id.cardInstitucional).setOnClickListener {
            Toast.makeText(this, "Video institucional - Próximamente", Toast.LENGTH_SHORT).show()
        }

        // === Barra lateral ===
        findViewById<LinearLayout>(R.id.menuPerfil).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }


        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            Toast.makeText(this, "Ya estás en Video", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            startActivity(Intent(this, WebActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.menuBotones).setOnClickListener {
            startActivity(Intent(this, AccionesActivity::class.java))
        }
    }

    private fun reproducirVideo(video: Video) {
        vistaLista.visibility = View.GONE
        vistaReproductor.visibility = View.VISIBLE
        tituloVideoReproduciendo.text = video.titulo

        val appOrigin = "https://com.example.rastreadorgastos"

        val html = """
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body { margin: 0; padding: 0; background: #000; }
                iframe { width: 100%; height: 100%; border: 0; }
            </style>
        </head>
        <body>
            <iframe
                src="https://www.youtube.com/embed/${video.youtubeId}?autoplay=1&origin=$appOrigin"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen>
            </iframe>
        </body>
        </html>
    """.trimIndent()

        webViewVideo.loadDataWithBaseURL(
            appOrigin,
            html,
            "text/html",
            "utf-8",
            null
        )
    }

    private fun cerrarReproductor() {
        // Detener el video y volver a la lista
        webViewVideo.loadUrl("about:blank")
        vistaReproductor.visibility = View.GONE
        vistaLista.visibility = View.VISIBLE
    }

    // Manejar el botón atrás del sistema
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (vistaReproductor.visibility == View.VISIBLE) {
            cerrarReproductor()
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }
}