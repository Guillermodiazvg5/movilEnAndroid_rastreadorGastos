package com.example.rastreadorgastos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class IngresoAdapter(
    private val lista: List<Ingreso>,
    private val onEliminar: (Int) -> Unit
) : RecyclerView.Adapter<IngresoAdapter.IngresoViewHolder>() {

    class IngresoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.ingresoEmoji)
        val tipo: TextView = view.findViewById(R.id.ingresoTipo)
        val fecha: TextView = view.findViewById(R.id.ingresoFecha)
        val valor: TextView = view.findViewById(R.id.ingresoValor)
        val btnEliminar: TextView = view.findViewById(R.id.btnEliminarIngreso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngresoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingreso, parent, false)
        return IngresoViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngresoViewHolder, position: Int) {
        val ingreso = lista[position]
        holder.emoji.text = ingreso.emoji
        holder.tipo.text = ingreso.tipo
        holder.fecha.text = ingreso.fecha

        val formato = NumberFormat.getNumberInstance(Locale("es", "CO"))
        holder.valor.text = "$" + formato.format(ingreso.valor)

        holder.btnEliminar.setOnClickListener {
            onEliminar(position)
        }
    }

    override fun getItemCount() = lista.size
}