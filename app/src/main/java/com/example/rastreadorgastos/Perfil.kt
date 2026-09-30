package com.example.rastreadorgastos

data class Perfil(
    val nombre: String,
    val rol: String,
    val estudios: String,
    val experiencia: String,
    val rutaFoto: String  // Ruta local de la foto, o "" si no tiene
)