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

[Ver diseño en Figma](https://www.figma.com/design/dhDUFT3GzuXW99rnha1TFe/App-movil-Gestor-de-Gastos?node-id=0-1&t=joCfQK09YVUjn6Mu-1)


---

## 📥 Descargar APK

👉 [**Descargar ElPesito APK**](https://github.com/Guillermodiazvg5/movilEnAndroid_rastreadorGastos/releases/latest/download/app-release-ElPesitoV1.0.0.apk)

> ⚠️ Al instalar el APK, permite "Instalar apps de fuentes desconocidas" en tu teléfono.

---

## 📸 Capturas de pantalla

| Perfil del Usuario | Editar Perfil | Gastos |

|<img width="250" height="555" alt="PerfilDeUsuario" src="https://github.com/user-attachments/assets/ed28e112-fe03-40ae-bbec-c55023663c97" />
 |<img width="250" height="555" alt="EditarPerfil" src="https://github.com/user-attachments/assets/90b994ca-5d73-43b9-b3bb-49733ad9b305" />
 |<img width="250" height="555" alt="Gastos" src="https://github.com/user-attachments/assets/a9917a34-e56a-4ecf-8804-b8c6d2d82ad5" />
 |

| Agregar Gasto | Lista Vacía | Modo Oscuro |

|<img width="250" height="555" alt="AgregarGasto" src="https://github.com/user-attachments/assets/c2bb53f3-7492-4bc6-b757-d85454e64496" />
 |<img width="250" height="555" alt="ListaVacia" src="https://github.com/user-attachments/assets/8eaa2184-192d-40a2-9c7f-a9455e17a49b" />
|<img width="250" height="555" alt="ModoOscuro" src="https://github.com/user-attachments/assets/5a3e2232-7ee5-452a-9eff-11a3bf258fd0" /> |



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
