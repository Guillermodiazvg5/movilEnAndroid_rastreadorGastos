package com.example.rastreadorgastos

data class Video(
    val titulo: String,
    val descripcion: String,
    val youtubeId: String,   // ID del video de YouTube (ej: "ht_T0vZ4XFU")
    val esInstitucional: Boolean = false  // true si es el video institucional (Próximamente)
)