package com.example.rastreadorgastos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(
    private val lista: List<Video>,
    private val onVideoClick: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.findViewById(R.id.thumbnailImage)
        val titulo: TextView = view.findViewById(R.id.videoTitulo)
        val descripcion: TextView = view.findViewById(R.id.videoDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = lista[position]
        holder.titulo.text = video.titulo
        holder.descripcion.text = video.descripcion

        // Cargar la miniatura desde YouTube usando Glide
        val thumbnailUrl = "https://img.youtube.com/vi/${video.youtubeId}/mqdefault.jpg"
        Glide.with(holder.itemView.context)
            .load(thumbnailUrl)
            .placeholder(android.R.drawable.ic_media_play)
            .into(holder.thumbnail)

        holder.itemView.setOnClickListener {
            onVideoClick(video)
        }
    }

    override fun getItemCount() = lista.size
}