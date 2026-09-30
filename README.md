# 🐷 ElPesito - Rastreador de Gastos

Aplicación móvil para Android para el registro y control de gastos personales, con una interfaz moderna e intuitiva.

![Estado](https://img.shields.io/badge/Estado-En%20desarrollo-yellow)
![Plataforma](https://img.shields.io/badge/Plataforma-Android-green)
![Lenguaje](https://img.shields.io/badge/Lenguaje-Kotlin-purple)

---

## 📱 Descripción

**ElPesito** es una app de finanzas personales que permite a los usuarios:

- 📝 Registrar gastos con descripción, categoría y monto.
- 📅 Guardar la fecha y hora automáticamente.
- 🗑️ Eliminar gastos con doble confirmación.
- 💰 Ver el total gastado en el mes en tiempo real.
- 👤 Personalizar el perfil del usuario (nombre, rol, foto, estudios y experiencia).
- 🌓 Cambiar entre modo claro y oscuro.
- 💾 Persistencia local de datos en formato JSON.

---

## 🎨 Mockup

[Ver diseño en Figma](file:///C:/Users/guill/AppData/Local/Packages/5319275A.WhatsAppDesktop_cv1g1gvanyjgm/LocalState/sessions/77136A0B6746A04A5925EE24ABA6F943E30F1F09/transfers/2026-38/mockup_rastreador_gastos.html)

---

## 📸 Capturas de pantalla

| Perfil del Usuario | Editar Perfil | Gastos |
|:---:|:---:|:---:|
| ![Perfil](URL_DE_TU_CAPTURA_PERFIL) | ![Editar](URL_DE_TU_CAPTURA_EDITAR) | ![Gastos](URL_DE_TU_CAPTURA_GASTOS) |

| Agregar Gasto | Lista Vacía | Modo Oscuro |
|:---:|:---:|:---:|
| ![Agregar](URL_DE_TU_CAPTURA_AGREGAR) | ![Vacía](URL_DE_TU_CAPTURA_VACIA) | ![Oscuro](URL_DE_TU_CAPTURA_OSCURO) |

> 💡 **Tip:** Para subir las imágenes, arrástralas directamente en el editor de GitHub y el sistema te generará los enlaces automáticamente.

---

## 🛠️ Tecnologías

- **Lenguaje:** Kotlin
- **IDE:** Android Studio
- **Control de versiones:** Git & GitHub
- **Persistencia:** JSON (almacenamiento interno)
- **UI:** Material Design, RecyclerView, ConstraintLayout
- **Temas:** Modo claro / oscuro con `AppCompatDelegate`

---

## 🎯 Objetivo

Desarrollar una aplicación que permita a los usuarios llevar un control organizado de sus finanzas personales, facilitando la visualización de su situación financiera mediante una interfaz intuitiva.

---

## ✨ Funcionalidades implementadas

### 👤 Perfil del Usuario
- Visualización de perfil con foto, nombre, rol, estudios y experiencia.
- Edición completa del perfil (guardado persistente).
- Cambio de foto desde **galería** o **cámara**.
- Animación de entrada al abrir la pantalla.

### 💰 Gastos
- Listado de gastos con fecha y hora.
- Agregar gastos con nombre, monto, categoría y descripción opcional.
- Eliminar gastos con diálogo de confirmación.
- Total mensual calculado automáticamente.
- Mensaje amigable cuando la lista está vacía.

### 🎨 Interfaz
- Barra lateral de navegación con secciones: Perfil, Fotos, Video, Web, Gastos.
- Switch de modo oscuro/claro en la barra lateral.
- Diseño responsivo con Material Design.
- 4 colores de botones principales en la pantalla de Acciones.

---

## 📋 Requisitos

- **Android Studio** Hedgehog (2023.1.1) o superior.
- **SDK mínimo:** API 26 (Android 8.0 Oreo).
- **SDK objetivo:** API 34 (Android 14).
- **Kotlin:** 1.9.x o superior.

---

## 🚀 Cómo compilar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Guillermodiazvg5/movilEnAndroid_rastreadorGastos.git
