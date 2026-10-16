package com.example.rastreadorgastos

import android.content.Context
import org.json.JSONArray

object EtiquetaStorage {

    private const val ARCHIVO = "etiquetas.json"

    fun guardar(context: Context, lista: List<String>) {
        val jsonArray = JSONArray()
        for (tag in lista) {
            jsonArray.put(tag)
        }
        context.openFileOutput(ARCHIVO, Context.MODE_PRIVATE).use { output ->
            output.write(jsonArray.toString().toByteArray())
        }
    }

    fun cargar(context: Context): MutableList<String> {
        val lista = mutableListOf<String>()
        try {
            val contenido = context.openFileInput(ARCHIVO).bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(contenido)
            for (i in 0 until jsonArray.length()) {
                lista.add(jsonArray.getString(i))
            }
        } catch (_: Exception) {
            // Lista vacía inicial
        }
        return lista
    }
}
