package com.example.rastreadorgastos

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var imgFoto: ImageView
    private lateinit var emojiFoto: TextView
    private lateinit var etNombre: EditText
    private lateinit var etRol: EditText
    private lateinit var etEstudios: EditText
    private lateinit var etExperiencia: EditText

    private var rutaFotoActual: String = ""
    private var uriFotoTemporal: Uri? = null
    private var archivoFotoTemporal: File? = null

    private val CODIGO_SELECCIONAR_IMAGEN = 200
    private val CODIGO_TOMAR_FOTO = 201
    private val CODIGO_PERMISO_CAMARA = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        imgFoto = findViewById(R.id.imgEditarFoto)
        emojiFoto = findViewById(R.id.emojiEditarFoto)
        etNombre = findViewById(R.id.etNombre)
        etRol = findViewById(R.id.etRol)
        etEstudios = findViewById(R.id.etEstudios)
        etExperiencia = findViewById(R.id.etExperiencia)

        // Cargar datos actuales
        val perfil = PerfilStorage.cargar(this)
        etNombre.setText(perfil.nombre)
        etRol.setText(perfil.rol)
        etEstudios.setText(perfil.estudios)
        etExperiencia.setText(perfil.experiencia)
        rutaFotoActual = perfil.rutaFoto

        if (rutaFotoActual.isNotEmpty()) {
            val archivo = File(rutaFotoActual)
            if (archivo.exists()) {
                val bitmap = BitmapFactory.decodeFile(archivo.absolutePath)
                imgFoto.setImageBitmap(bitmap)
                emojiFoto.visibility = TextView.GONE
            }
        }

        // Botón cambiar foto → muestra diálogo
        findViewById<TextView>(R.id.btnCambiarFoto).setOnClickListener {
            mostrarDialogoOpcionesFoto()
        }

        // Botón guardar
        findViewById<Button>(R.id.btnGuardarPerfil).setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val rol = etRol.text.toString().trim()
            val estudios = etEstudios.text.toString().trim()
            val experiencia = etExperiencia.text.toString().trim()

            if (nombre.isEmpty() || rol.isEmpty()) {
                Toast.makeText(this, "Nombre y rol son obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                val perfilActualizado = Perfil(nombre, rol, estudios, experiencia, rutaFotoActual)
                PerfilStorage.guardar(this, perfilActualizado)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun mostrarDialogoOpcionesFoto() {
        val opciones = arrayOf("📷 Tomar foto", "🖼️ Elegir de la galería")

        AlertDialog.Builder(this)
            .setTitle("Cambiar foto de perfil")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> verificarPermisoCamara()
                    1 -> abrirGaleria()
                }
            }
            .show()
    }

    private fun verificarPermisoCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            abrirCamara()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CODIGO_PERMISO_CAMARA)
        }
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            // Crear archivo temporal para guardar la foto
            archivoFotoTemporal = File.createTempFile("foto_perfil_", ".jpg", filesDir)
            uriFotoTemporal = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                archivoFotoTemporal!!
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoTemporal)
            startActivityForResult(intent, CODIGO_TOMAR_FOTO)
        } else {
            Toast.makeText(this, "No hay app de cámara disponible", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, CODIGO_SELECCIONAR_IMAGEN)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODIGO_PERMISO_CAMARA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            CODIGO_SELECCIONAR_IMAGEN -> {
                val uri = data?.data ?: return
                try {
                    val inputStream = contentResolver.openInputStream(uri) ?: return
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream.close()
                    guardarFoto(bitmap)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
                }
            }
            CODIGO_TOMAR_FOTO -> {
                try {
                    val bitmap = BitmapFactory.decodeFile(archivoFotoTemporal?.absolutePath)
                    if (bitmap != null) {
                        guardarFoto(bitmap)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al procesar la foto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun guardarFoto(bitmap: Bitmap) {
        val archivoDestino = File(filesDir, "perfil_foto.jpg")
        val outputStream = FileOutputStream(archivoDestino)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        outputStream.close()

        rutaFotoActual = archivoDestino.absolutePath

        imgFoto.setImageBitmap(bitmap)
        emojiFoto.visibility = TextView.GONE
    }
}