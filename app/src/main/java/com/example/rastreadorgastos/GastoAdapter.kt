package com.example.rastreadorgastos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GastoAdapter(
    private val lista: List<Gasto>,
    private val onEliminar: (Int) -> Unit
) : RecyclerView.Adapter<GastoAdapter.GastoViewHolder>() {

    class GastoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.gastoEmoji)
        val nombre: TextView = view.findViewById(R.id.gastoNombre)
        val detalle: TextView = view.findViewById(R.id.gastoDetalle)
        val descripcion: TextView = view.findViewById(R.id.gastoDescripcion)
        val monto: TextView = view.findViewById(R.id.gastoMonto)
        val btnEliminar: TextView = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gasto, parent, false)
        return GastoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val gasto = lista[position]
        holder.emoji.text = gasto.emoji
        holder.nombre.text = gasto.nombre
        holder.detalle.text = "${gasto.categoria} · ${gasto.fechaHora}"
        holder.monto.text = gasto.monto

        if (gasto.descripcion.isNotEmpty()) {
            holder.descripcion.text = gasto.descripcion
            holder.descripcion.visibility = View.VISIBLE
        } else {
            holder.descripcion.visibility = View.GONE
        }

        holder.btnEliminar.setOnClickListener {
            onEliminar(position)
        }
    }

    override fun getItemCount() = lista.size
}