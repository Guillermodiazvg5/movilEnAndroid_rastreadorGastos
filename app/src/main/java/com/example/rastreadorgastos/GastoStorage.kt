package com.example.rastreadorgastos

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object GastoStorage {

    private const val ARCHIVO = "gastos.json"

    fun guardar(context: Context, lista: List<Gasto>) {
        val jsonArray = JSONArray()
        for (gasto in lista) {
            val jsonObj = JSONObject()
            jsonObj.put("emoji", gasto.emoji)
            jsonObj.put("nombre", gasto.nombre)
            jsonObj.put("categoria", gasto.categoria)
            jsonObj.put("descripcion", gasto.descripcion)
            jsonObj.put("monto", gasto.monto)
            jsonObj.put("fechaHora", gasto.fechaHora)
            jsonArray.put(jsonObj)
        }
        context.openFileOutput(ARCHIVO, Context.MODE_PRIVATE).use { output ->
            output.write(jsonArray.toString().toByteArray())
        }
    }

    fun cargar(context: Context): MutableList<Gasto> {
        val lista = mutableListOf<Gasto>()
        try {
            val contenido = context.openFileInput(ARCHIVO).bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(contenido)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                lista.add(
                    Gasto(
                        obj.getString("emoji"),
                        obj.getString("nombre"),
                        obj.getString("categoria"),
                        obj.optString("descripcion", ""),
                        obj.getString("monto"),
                        obj.optString("fechaHora", "")
                    )
                )
            }
        } catch (e: Exception) {
            // Lista vacía si no hay archivo
        }
        return lista
    }

    // Método para borrar todo (útil al inicio)
    fun borrarTodo(context: Context) {
        context.deleteFile(ARCHIVO)
    }
}