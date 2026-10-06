package com.example.rastreadorgastos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class WebActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var urlInput: EditText
    private lateinit var loadBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        // === Referencias a las vistas ===
        webView = findViewById(R.id.webView)
        urlInput = findViewById(R.id.urlInput)
        loadBtn = findViewById(R.id.loadBtn)

        // === Configurar WebView ===
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            displayZoomControls = false
        }

        webView.clearCache(true)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (url != null) {
                    urlInput.setText(url)
                }
            }
        }

        // Verificar si viene un ID de YouTube desde otra Activity
        val youtubeId = intent.getStringExtra("youtube_id")
        if (youtubeId != null) {
            val url = "https://www.youtube.com/watch?v=$youtubeId"
            urlInput.setText(url)
            loadUrl(url)
        } else {
            val defaultUrl = "https://google.com"
            urlInput.setText(defaultUrl)
            loadUrl(defaultUrl)
        }

        // === Botón Cargar ===
        loadBtn.setOnClickListener {
            loadFromInput()
        }

        // === Cargar con Enter en el teclado ===
        urlInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                loadFromInput()
                true
            } else {
                false
            }
        }

        // === Botón atrás dentro del WebView ===
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        // === Barra lateral ===
        setupSidebar()
    }

    private fun loadFromInput() {
        var url = urlInput.text.toString().trim()
        if (url.isNotEmpty()) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }
            loadUrl(url)
            // Ocultar el teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(urlInput.windowToken, 0)
        }
    }

    private fun loadUrl(url: String) {
        webView.loadUrl(url)
    }

    private fun setupSidebar() {
        findViewById<LinearLayout>(R.id.menuPerfil).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.menuGastos).setOnClickListener {
            val intent = Intent(this, GastosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }


        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.menuWeb).setOnClickListener {
            Toast.makeText(this, "Ya estás en Web", Toast.LENGTH_SHORT).show()
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
        }
    }
}