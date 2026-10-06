package com.example.rastreadorgastos

data class Ingreso(
    val tipo: String,       // "Salario mensual", "Ganancias ocasionales", etc.
    val emoji: String,      // "💼", "🎁", "🏪", "🚀"
    val valor: Int,         // Valor en pesos (sin decimales)
    val fecha: String       // "01/10/2026"
)