package com.example.rastreadorgastos

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
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

        webView = findViewById(R.id.webView)
        urlInput = findViewById(R.id.urlInput)
        loadBtn = findViewById(R.id.loadBtn)

        // Configurar WebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (url != null) {
                    urlInput.setText(url)
                }
            }
        }

        val defaultUrl = "https://finanzaspersonales.com.co"
        urlInput.setText(defaultUrl)
        loadUrl(defaultUrl)

        loadBtn.setOnClickListener {
            loadFromInput()
        }

        urlInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                loadFromInput()
                true
            } else {
                false
            }
        }

        // Manejar botón atrás en WebView
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

        // ===== Barra lateral =====
        setupSidebar()
    }

    private fun loadFromInput() {
        var url = urlInput.text.toString().trim()
        if (url.isNotEmpty()) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }
            loadUrl(url)
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

        findViewById<LinearLayout>(R.id.menuFotos).setOnClickListener {
            Toast.makeText(this, "Fotos - Próximamente", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.menuVideo).setOnClickListener {
            Toast.makeText(this, "Video - Próximamente", Toast.LENGTH_SHORT).show()
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
    }
}
