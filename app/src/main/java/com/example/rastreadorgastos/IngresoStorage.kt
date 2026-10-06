package com.example.rastreadorgastos

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object IngresoStorage {

    private const val ARCHIVO = "ingresos.json"

    fun guardar(context: Context, lista: List<Ingreso>) {
        val jsonArray = JSONArray()
        for (ingreso in lista) {
            val jsonObj = JSONObject()
            jsonObj.put("tipo", ingreso.tipo)
            jsonObj.put("emoji", ingreso.emoji)
            jsonObj.put("valor", ingreso.valor)
            jsonObj.put("fecha", ingreso.fecha)
            jsonArray.put(jsonObj)
        }
        context.openFileOutput(ARCHIVO, Context.MODE_PRIVATE).use { output ->
            output.write(jsonArray.toString().toByteArray())
        }
    }

    fun cargar(context: Context): MutableList<Ingreso> {
        val lista = mutableListOf<Ingreso>()
        try {
            val contenido = context.openFileInput(ARCHIVO).bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(contenido)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                lista.add(
                    Ingreso(
                        obj.getString("tipo"),
                        obj.getString("emoji"),
                        obj.getInt("valor"),
                        obj.getString("fecha")
                    )
                )
            }
        } catch (e: Exception) {
            // Lista vacía si no hay archivo
        }
        return lista
    }
}