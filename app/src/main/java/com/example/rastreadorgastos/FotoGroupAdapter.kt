package com.example.rastreadorgastos

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

sealed class GalleryItem {
    data class Header(val etiqueta: String) : GalleryItem()
    data class PhotoItem(val foto: Foto) : GalleryItem()
}

class FotoGroupAdapter(
    private val items: List<GalleryItem>,
    private val isSelectionMode: Boolean,
    private val onFotoClick: (Foto) -> Unit,
    private val onFotoLongClick: () -> Unit,
    private val onSelectionChanged: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_PHOTO = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is GalleryItem.Header -> TYPE_HEADER
            is GalleryItem.PhotoItem -> TYPE_PHOTO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header_etiqueta, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_foto, parent, false)
            PhotoViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is GalleryItem.Header -> {
                val headerHolder = holder as HeaderViewHolder
                headerHolder.tvHeader.text = "🏷️ Etiqueta: ${item.etiqueta.uppercase()}"
            }
            is GalleryItem.PhotoItem -> {
                val photoHolder = holder as PhotoViewHolder
                val foto = item.foto
                photoHolder.tvTitulo.text = foto.titulo
                photoHolder.tvDescripcion.text = foto.descripcion

                if (isSelectionMode) {
                    photoHolder.checkbox.visibility = View.VISIBLE
                } else {
                    photoHolder.checkbox.visibility = View.GONE
                }

                photoHolder.checkbox.setOnCheckedChangeListener(null)
                photoHolder.checkbox.isChecked = foto.isSelected

                photoHolder.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    foto.isSelected = isChecked
                    onSelectionChanged()
                }

                photoHolder.itemView.setOnClickListener {
                    onFotoClick(foto)
                }

                photoHolder.itemView.setOnLongClickListener {
                    foto.isSelected = true
                    onFotoLongClick()
                    true
                }

                if (foto.imageUri.isNotEmpty()) {
                    Glide.with(photoHolder.itemView.context)
                        .load(Uri.parse(foto.imageUri))
                        .centerCrop()
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .into(photoHolder.imgThumbnail)
                } else {
                    photoHolder.imgThumbnail.setImageResource(android.R.drawable.ic_menu_gallery)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHeader: TextView = view.findViewById(R.id.tvHeaderEtiqueta)
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgThumbnail: ImageView = view.findViewById(R.id.imgFotoThumbnail)
        val tvTitulo: TextView = view.findViewById(R.id.fotoTitulo)
        val tvDescripcion: TextView = view.findViewById(R.id.fotoDescripcionPreview)
        val checkbox: CheckBox = view.findViewById(R.id.checkboxFoto)
    }
}
