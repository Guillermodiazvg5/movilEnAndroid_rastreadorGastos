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

[Ver diseño en Figma]([mockup_rastreador_gastos.html](https://github.com/user-attachments/files/32837041/mockup_rastreador_gastos.html)
)<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mockup - ElPesito</title>
    <!-- Modern Typography -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --bg-color: #0f172a;
            --surface-color: rgba(30, 41, 59, 0.7);
            --primary-color: #6366f1;
            --primary-hover: #4f46e5;
            --text-primary: #f8fafc;
            --text-secondary: #94a3b8;
            --border-color: rgba(255, 255, 255, 0.1);
            --glass-bg: rgba(255, 255, 255, 0.03);
            --glass-border: rgba(255, 255, 255, 0.05);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background-color: var(--bg-color);
            color: var(--text-primary);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background-image: radial-gradient(circle at 15% 50%, rgba(99, 102, 241, 0.15), transparent 25%),
                              radial-gradient(circle at 85% 30%, rgba(16, 185, 129, 0.1), transparent 25%);
        }

        /* App Container mimicking an Android tablet/landscape view */
        .app-container {
            width: 90%;
            max-width: 1200px;
            height: 85vh;
            background: var(--surface-color);
            backdrop-filter: blur(16px);
            -webkit-backdrop-filter: blur(16px);
            border: 1px solid var(--border-color);
            border-radius: 24px;
            display: flex;
            overflow: hidden;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
        }

        /* Left Menu Fragment */
        .left-menu {
            width: 300px;
            background: var(--glass-bg);
            border-right: 1px solid var(--border-color);
            display: flex;
            flex-direction: column;
            padding: 24px 0;
        }

        .app-brand {
            padding: 0 24px 32px 24px;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .app-brand-icon {
            font-size: 28px;
        }

        .app-brand-text {
            font-size: 20px;
            font-weight: 700;
            background: linear-gradient(to right, #818cf8, #c084fc);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }

        .menu-list {
            list-style: none;
            display: flex;
            flex-direction: column;
            gap: 8px;
            padding: 0 16px;
        }

        .menu-item {
            padding: 16px 24px;
            border-radius: 12px;
            cursor: pointer;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
            color: var(--text-secondary);
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .menu-item:hover {
            background: rgba(255, 255, 255, 0.05);
            color: var(--text-primary);
            transform: translateX(4px);
        }

        .menu-item.active {
            background: var(--primary-color);
            color: white;
            box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
        }

        /* Right Content Fragment */
        .right-content {
            flex: 1;
            padding: 40px;
            overflow-y: auto;
            position: relative;
        }

        /* Scrollbar styles */
        ::-webkit-scrollbar {
            width: 8px;
        }
        ::-webkit-scrollbar-track {
            background: transparent;
        }
        ::-webkit-scrollbar-thumb {
            background: rgba(255, 255, 255, 0.1);
            border-radius: 10px;
        }
        ::-webkit-scrollbar-thumb:hover {
            background: rgba(255, 255, 255, 0.2);
        }

        /* View Containers */
        .view-section {
            display: none;
            animation: fadeIn 0.4s ease forwards;
        }

        .view-section.active {
            display: block;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2.section-title {
            font-size: 28px;
            margin-bottom: 24px;
            font-weight: 600;
        }

        /* --- Perfil View Styles --- */
        .profile-card {
            background: var(--glass-bg);
            border: 1px solid var(--border-color);
            border-radius: 20px;
            padding: 32px;
            text-align: center;
            max-width: 600px;
            margin: 0 auto;
        }

        .profile-img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: linear-gradient(135deg, #6366f1, #a855f7);
            margin: 0 auto 20px auto;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            box-shadow: 0 10px 25px rgba(99, 102, 241, 0.4);
        }

        .profile-name {
            font-size: 24px;
            font-weight: 700;
            margin-bottom: 8px;
        }

        .profile-role {
            color: var(--primary-color);
            font-weight: 500;
            margin-bottom: 24px;
        }

        .profile-details {
            text-align: left;
            background: rgba(0, 0, 0, 0.2);
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 16px;
        }
        
        .profile-details h3 {
            font-size: 16px;
            color: var(--text-secondary);
            margin-bottom: 8px;
        }

        /* --- Fotos View Styles --- */
        .photo-list {
            display: flex;
            flex-direction: column;
            gap: 16px;
        }

        .photo-card {
            display: flex;
            background: var(--glass-bg);
            border: 1px solid var(--border-color);
            border-radius: 16px;
            padding: 16px;
            align-items: center;
            gap: 20px;
            transition: transform 0.2s;
        }

        .photo-card:hover {
            transform: scale(1.02);
            background: rgba(255,255,255,0.06);
        }

        .photo-thumbnail {
            width: 80px;
            height: 80px;
            border-radius: 12px;
            background: #334155;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
        }

        .photo-info h4 {
            font-size: 18px;
            margin-bottom: 4px;
        }
        .photo-info p {
            color: var(--text-secondary);
            font-size: 14px;
        }

        /* --- Video View Styles --- */
        .video-container {
            width: 100%;
            max-width: 700px;
            margin: 0 auto;
            aspect-ratio: 16 / 9;
            background: #000;
            border-radius: 16px;
            border: 1px solid var(--border-color);
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            box-shadow: 0 20px 40px rgba(0,0,0,0.4);
        }

        .play-btn {
            width: 64px;
            height: 64px;
            background: var(--primary-color);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            cursor: pointer;
            transition: transform 0.2s;
            box-shadow: 0 0 20px rgba(99, 102, 241, 0.6);
        }

        .play-btn:hover {
            transform: scale(1.1);
        }

        .video-controls {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 48px;
            background: linear-gradient(transparent, rgba(0,0,0,0.8));
            display: flex;
            align-items: center;
            padding: 0 16px;
            gap: 12px;
        }
        
        .progress-bar {
            flex: 1;
            height: 4px;
            background: rgba(255,255,255,0.3);
            border-radius: 2px;
        }
        .progress-fill {
            width: 35%;
            height: 100%;
            background: var(--primary-color);
            border-radius: 2px;
        }

        /* --- Web View Styles --- */
        .web-browser {
            display: flex;
            flex-direction: column;
            height: 100%;
            gap: 16px;
        }

        .web-bar {
            display: flex;
            gap: 12px;
            background: var(--glass-bg);
            padding: 12px;
            border-radius: 12px;
            border: 1px solid var(--border-color);
        }

        .web-input {
            flex: 1;
            background: rgba(0,0,0,0.2);
            border: 1px solid var(--border-color);
            border-radius: 8px;
            padding: 10px 16px;
            color: white;
            font-size: 14px;
            outline: none;
            transition: border-color 0.2s;
        }

        .web-input:focus {
            border-color: var(--primary-color);
        }

        .web-btn {
            background: var(--primary-color);
            color: white;
            border: none;
            padding: 0 24px;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }

        .web-btn:hover {
            background: var(--primary-hover);
        }

        .web-content {
            flex: 1;
            background: #ffffff;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #334155;
            font-weight: 500;
            min-height: 400px;
        }

        /* --- Botones View Styles --- */
        .buttons-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 24px;
            margin-top: 32px;
        }

        .action-btn {
            padding: 20px;
            border-radius: 16px;
            border: none;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 12px;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
        }

        .action-btn i {
            font-size: 32px;
            font-style: normal;
        }

        .btn-primary {
            background: linear-gradient(135deg, #6366f1, #818cf8);
            color: white;
            box-shadow: 0 10px 20px rgba(99, 102, 241, 0.3);
        }

        .btn-primary:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 25px rgba(99, 102, 241, 0.4);
        }

        .btn-secondary {
            background: linear-gradient(135deg, #10b981, #34d399);
            color: white;
            box-shadow: 0 10px 20px rgba(16, 185, 129, 0.3);
        }

        .btn-secondary:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 25px rgba(16, 185, 129, 0.4);
        }

        .btn-outline {
            background: transparent;
            border: 2px solid var(--primary-color);
            color: white;
        }

        .btn-outline:hover {
            background: rgba(99, 102, 241, 0.1);
            transform: translateY(-5px);
        }
        
        .btn-ghost {
            background: var(--glass-bg);
            border: 1px solid var(--border-color);
            color: white;
        }
        .btn-ghost:hover {
            background: rgba(255,255,255,0.1);
            transform: translateY(-5px);
        }
    </style>
</head>
<body>

    <div class="app-container">
        
        <!-- Left Menu -->
        <div class="left-menu">
            <div class="app-brand">
                <span class="app-brand-icon">💰</span>
                <span class="app-brand-text">ElPesito</span>
            </div>
            
            <ul class="menu-list">
                <li class="menu-item active" data-target="perfil">
                    <span>👤</span> Perfil
                </li>
                <li class="menu-item" data-target="fotos">
                    <span>📸</span> Fotos
                </li>
                <li class="menu-item" data-target="video">
                    <span>🎬</span> Video
                </li>
                <li class="menu-item" data-target="web">
                    <span>🌐</span> Web
                </li>
                <li class="menu-item" data-target="botones">
                    <span>🔘</span> Botones
                </li>
            </ul>
        </div>

        <!-- Right Content -->
        <div class="right-content">
            
            <!-- Perfil View -->
            <div id="perfil" class="view-section active">
                <h2 class="section-title">Perfil del Usuario</h2>
                <div class="profile-card">
                    <div class="profile-img">🧑‍💼</div>
                    <div class="profile-name">Juan Pérez</div>
                    <div class="profile-role">Analista Financiero</div>
                    
                    <div class="profile-details">
                        <h3>🎓 Estudios</h3>
                        <p>Contaduría Pública - Politécnico Grancolombiano</p>
                        <p style="font-size: 14px; color: var(--text-secondary); margin-top: 4px;">Especialización en Finanzas Personales.</p>
                    </div>
                    
                    <div class="profile-details">
                        <h3>💼 Experiencia</h3>
                        <p>Más de 5 años de experiencia gestionando presupuestos y controlando finanzas personales.</p>
                        <p style="font-size: 14px; color: var(--text-secondary); margin-top: 4px;">Experto en estrategias de ahorro e inversiones a largo plazo. Ha dictado seminarios sobre libertad financiera y economía del hogar.</p>
                    </div>
                </div>
            </div>

            <!-- Fotos View -->
            <div id="fotos" class="view-section">
                <h2 class="section-title">Fotos de Facturas</h2>
                <div class="photo-list">
                    <!-- Photo Items -->
                    <div class="photo-card">
                        <div class="photo-thumbnail">🛒</div>
                        <div class="photo-info">
                            <h4>Supermercado - Mercado Libre</h4>
                            <p>Fecha: 15/09/2026 • Total: $150.000</p>
                        </div>
                    </div>
                    <div class="photo-card">
                        <div class="photo-thumbnail">⚡</div>
                        <div class="photo-info">
                            <h4>Recibo de Luz - Enel</h4>
                            <p>Fecha: 12/09/2026 • Total: $80.000</p>
                        </div>
                    </div>
                    <div class="photo-card">
                        <div class="photo-thumbnail">⛽</div>
                        <div class="photo-info">
                            <h4>Tiquete de Gasolina - Terpel</h4>
                            <p>Fecha: 10/09/2026 • Total: $60.000</p>
                        </div>
                    </div>
                    <div class="photo-card">
                        <div class="photo-thumbnail">🍽️</div>
                        <div class="photo-info">
                            <h4>Cena Restaurante</h4>
                            <p>Fecha: 08/09/2026 • Total: $45.000</p>
                        </div>
                    </div>
                    <div class="photo-card">
                        <div class="photo-thumbnail">💻</div>
                        <div class="photo-info">
                            <h4>Suscripción Software</h4>
                            <p>Fecha: 01/09/2026 • Total: $35.000</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Video View -->
            <div id="video" class="view-section">
                <h2 class="section-title">Educación Financiera</h2>
                <p style="margin-bottom: 24px; color: var(--text-secondary);">Tutorial: Estrategias prácticas para ahorrar dinero mensualmente.</p>
                
                <div class="video-container">
                    <div class="play-btn">▶️</div>
                    <div class="video-controls">
                        <span style="font-size: 12px; font-variant-numeric: tabular-nums;">01:23</span>
                        <div class="progress-bar">
                            <div class="progress-fill"></div>
                        </div>
                        <span style="font-size: 12px; font-variant-numeric: tabular-nums;">05:30</span>
                        <span style="cursor:pointer">⚙️</span>
                        <span style="cursor:pointer">⛶</span>
                    </div>
                </div>
            </div>

            <!-- Web View -->
            <div id="web" class="view-section">
                <h2 class="section-title">Navegador Web Integrado</h2>
                <div class="web-browser">
                    <div class="web-bar">
                        <input type="text" class="web-input" id="urlInput" value="https://finanzaspersonales.com.co">
                        <button class="web-btn" id="loadBtn">Cargar</button>
                    </div>
                    <div class="web-content" id="webFrame">
                        Simulación de WebView Android.<br><br>Cargando: https://finanzaspersonales.com.co
                    </div>
                </div>
            </div>

            <!-- Botones View -->
            <div id="botones" class="view-section">
                <h2 class="section-title">Acciones Principales</h2>
                <p style="color: var(--text-secondary);">Panel de control de acciones rápidas para el manejo de finanzas.</p>
                
                <div class="buttons-grid">
                    <button class="action-btn btn-primary">
                        <i>💸</i>
                        Registrar Nuevo Gasto
                    </button>
                    
                    <button class="action-btn btn-secondary">
                        <i>📊</i>
                        Ver Gráfico Mensual
                    </button>
                    
                    <button class="action-btn btn-outline">
                        <i>🎯</i>
                        Definir Presupuesto
                    </button>
                    
                    <button class="action-btn btn-ghost">
                        <i>🧾</i>
                        Escanear Factura
                    </button>
                </div>
            </div>

        </div>
    </div>

    <!-- Interactivity Script -->
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const menuItems = document.querySelectorAll('.menu-item');
            const viewSections = document.querySelectorAll('.view-section');

            // Tabs Logic
            menuItems.forEach(item => {
                item.addEventListener('click', () => {
                    // Remove active from all menus
                    menuItems.forEach(m => m.classList.remove('active'));
                    // Add active to clicked menu
                    item.classList.add('active');

                    // Hide all views
                    viewSections.forEach(v => v.classList.remove('active'));
                    
                    // Show target view
                    const targetId = item.getAttribute('data-target');
                    const targetView = document.getElementById(targetId);
                    if(targetView) {
                        targetView.classList.add('active');
                    }
                });
            });

            // Web View Mock Logic
            const loadBtn = document.getElementById('loadBtn');
            const urlInput = document.getElementById('urlInput');
            const webFrame = document.getElementById('webFrame');

            loadBtn.addEventListener('click', () => {
                const url = urlInput.value;
                webFrame.innerHTML = `Simulación de WebView Android.<br><br>Cargando: ${url}`;
                webFrame.style.animation = 'none';
                webFrame.offsetHeight; // trigger reflow
                webFrame.style.animation = 'fadeIn 0.4s ease';
            });
            
            urlInput.addEventListener('keypress', (e) => {
                if(e.key === 'Enter') loadBtn.click();
            });
        });
    </script>
</body>
</html>


---

## 📸 Capturas de pantalla

| Perfil del Usuario | Editar Perfil | Gastos |
|:---:|:---:|:---:|
| ![Perfil](<img width="720" height="1600" alt="PerfilDeUsuario" src="https://github.com/user-attachments/assets/ed28e112-fe03-40ae-bbec-c55023663c97" />
) | ![Editar](<img width="720" height="1600" alt="EditarPerfil" src="https://github.com/user-attachments/assets/90b994ca-5d73-43b9-b3bb-49733ad9b305" />
) | ![Gastos](<img width="720" height="1600" alt="Gastos" src="https://github.com/user-attachments/assets/a9917a34-e56a-4ecf-8804-b8c6d2d82ad5" />
) |

| Agregar Gasto | Lista Vacía | Modo Oscuro |
|:---:|:---:|:---:|
| ![Agregar](<img width="720" height="1600" alt="AgregarGasto" src="https://github.com/user-attachments/assets/c2bb53f3-7492-4bc6-b757-d85454e64496" />
) | ![Vacía](<img width="720" height="1600" alt="ListaVacia" src="https://github.com/user-attachments/assets/8eaa2184-192d-40a2-9c7f-a9455e17a49b" />
) | ![Oscuro](<img width="720" height="1600" alt="ModoOscuro" src="https://github.com/user-attachments/assets/5a3e2232-7ee5-452a-9eff-11a3bf258fd0" />
) |



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
