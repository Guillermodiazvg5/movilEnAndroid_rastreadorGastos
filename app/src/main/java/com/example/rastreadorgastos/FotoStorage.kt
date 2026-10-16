package com.example.rastreadorgastos

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

object FotoStorage {

    private const val ARCHIVO = "fotos.json"

    fun guardar(context: Context, lista: List<Foto>) {
        val jsonArray = JSONArray()
        for (foto in lista) {
            val jsonObj = JSONObject()
            jsonObj.put("id", foto.id)
            jsonObj.put("titulo", foto.titulo)
            jsonObj.put("descripcion", foto.descripcion)
            jsonObj.put("etiqueta", foto.etiqueta)
            jsonObj.put("imageUri", foto.imageUri)
            jsonArray.put(jsonObj)
        }
        context.openFileOutput(ARCHIVO, Context.MODE_PRIVATE).use { output ->
            output.write(jsonArray.toString().toByteArray())
        }
    }

    fun cargar(context: Context): MutableList<Foto> {
        val lista = mutableListOf<Foto>()
        try {
            val contenido = context.openFileInput(ARCHIVO).bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(contenido)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                lista.add(
                    Foto(
                        obj.optString("id", UUID.randomUUID().toString()),
                        obj.getString("titulo"),
                        obj.optString("descripcion", ""),
                        obj.optString("etiqueta", "sin etiqueta"),
                        obj.optString("imageUri", "")
                    )
                )
            }
        } catch (_: Exception) {
            // Lista vacía inicial si no hay archivo
        }
        return lista
    }
}
