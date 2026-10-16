package com.example.rastreadorgastos

data class Foto(
    val id: String,
    var titulo: String,
    val descripcion: String,
    var etiqueta: String = "sin etiqueta",
    val imageUri: String = "",
    var isSelected: Boolean = false
)
