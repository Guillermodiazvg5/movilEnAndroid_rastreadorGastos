package com.example.rastreadorgastos

data class Gasto(
    val emoji: String,
    val nombre: String,
    val categoria: String,
    val descripcion: String,   // Puede estar vacío
    val monto: String,
    val fechaHora: String      // Ej: "15/09/2026 14:30"
)