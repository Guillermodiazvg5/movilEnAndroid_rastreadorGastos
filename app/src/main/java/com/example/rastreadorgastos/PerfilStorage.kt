package com.example.rastreadorgastos

import android.content.Context
import org.json.JSONObject

object PerfilStorage {

    private const val ARCHIVO = "perfil.json"

    fun guardar(context: Context, perfil: Perfil) {
        val json = JSONObject()
        json.put("nombre", perfil.nombre)
        json.put("rol", perfil.rol)
        json.put("estudios", perfil.estudios)
        json.put("experiencia", perfil.experiencia)
        json.put("rutaFoto", perfil.rutaFoto)
        context.openFileOutput(ARCHIVO, Context.MODE_PRIVATE).use { output ->
            output.write(json.toString().toByteArray())
        }
    }

    fun cargar(context: Context): Perfil {
        return try {
            val contenido = context.openFileInput(ARCHIVO).bufferedReader().use { it.readText() }
            val json = JSONObject(contenido)
            Perfil(
                json.getString("nombre"),
                json.getString("rol"),
                json.getString("estudios"),
                json.getString("experiencia"),
                json.optString("rutaFoto", "")
            )
        } catch (e: Exception) {
            // Datos por defecto la primera vez
            Perfil(
                "Juan Pérez",
                "Analista Financiero",
                "Contaduría Pública - Politécnico Grancolombiano",
                "Más de 5 años de experiencia gestionando presupuestos y controlando finanzas personales.",
                ""
            )
        }
    }
}