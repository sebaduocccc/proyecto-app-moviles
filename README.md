# Master Martini App

Aplicación móvil para **Master Martini Chile**, empresa que vende insumos para panaderías, pastelerías, heladerías y otros negocios gastronómicos.

La app reúne en un solo lugar recetas, videos técnicos y el catálogo de productos de la marca, para que los clientes aprendan a usar los insumos y la empresa mantenga una relación más cercana con ellos.

> Prototipo académico desarrollado para la asignatura **DSY1105 Desarrollo de Aplicaciones Móviles** (Duoc UC).

## Integrantes

| Nombre | Tareas principales |
|---|---|
| _Ignacio Ponce_ | _Home y Catalogo (Main Screen)_ |
| _Sebastián Saavedra_ | _Usuarios y Administración_ |
| _Cristóbal Spínola_ | _Perfiles de Usuario y sus Roles_ |

Equipo N° _X_

## Funcionalidades

- **Inicio de sesión y registro:** crear una cuenta e ingresar con correo y contraseña. Cada campo avisa si hay un error.
- **Inicio:** listado de recetas con buscador y categorías.
- **Detalle de receta:** ingredientes, tiempo de preparación, pasos y video explicativo.
- **Catálogo:** productos de Master Martini con buscador.
- **Detalle de producto:** información del producto y recetas en las que se usa.
- **Perfil:** ver y editar los datos personales, cambiar la foto desde la galería y cerrar sesión.
- **Panel de administración:** el administrador puede ver, buscar, crear, editar y eliminar usuarios.

## Tecnologías

- **Kotlin** y **Jetpack Compose** para construir las pantallas.
- **Material 3** para el diseño visual.
- **Navigation Compose** para moverse entre pantallas.
- **Room** para guardar los usuarios en el teléfono.
- **Coil** para mostrar imágenes y **Android YouTube Player** para los videos.

## Estructura del proyecto

El proyecto sigue el patrón **MVVM**: cada pantalla separa lo que se ve (la pantalla) de la lógica (el ViewModel) y de los datos (los repositorios).

```
app/src/main/java/com/mastermartini/
├── data/          Datos: modelos, base de datos y repositorios
├── di/            Crea y comparte los repositorios con toda la app
├── ui/            Pantallas, cada una en su carpeta (login, home, perfil, admin...)
├── components/    Piezas reutilizables (campos de formulario, botones, logo)
└── validation/    Reglas de validación de los formularios
```

## Persistencia local

Los datos de los usuarios se guardan en el teléfono con **Room** (una base de datos local), así que no se pierden al cerrar la app.

- **Usuarios:** se guardan en la tabla `usuarios`. La primera vez que se abre la app se cargan algunos usuarios de prueba desde `assets/usuarios.json`.
- **Sesión:** la app recuerda qué usuario inició sesión hasta que cierre sesión.
- **Recetas y productos:** por ahora son datos de ejemplo incluidos en la app.

## Validaciones

Los formularios de inicio de sesión, registro y del panel de administración revisan cada campo antes de guardar. Si algo está mal, el campo se marca en rojo con un ícono y un mensaje que explica el error.

| Campo | Regla |
|---|---|
| Nombre y apellido | Obligatorio, solo letras, mínimo 2 caracteres |
| Nombre de usuario | Entre 3 y 20 caracteres (letras, números, `.` y `_`) y que no esté en uso |
| Correo | Formato válido y que no esté registrado |
| Contraseña | Mínimo 6 caracteres, con al menos una letra y un número |
| Fecha de nacimiento | Obligatoria, no puede ser futura y requiere al menos 13 años |

Las reglas están en `validation/FormValidator.kt`, separadas de las pantallas, para reutilizarlas en todos los formularios.

## Animaciones

- Los mensajes de error aparecen y desaparecen suavemente bajo cada campo.
- El botón de enviar muestra un indicador de carga mientras se procesa.
- El formulario de inicio de sesión se sacude cuando los datos son incorrectos.
- En el panel de administración, las tarjetas de usuarios se reordenan con animación al buscar, crear o eliminar.

## Recursos nativos

| Recurso | Para qué se usa |
|---|---|
| **Galería de fotos** | Elegir la foto de perfil. Usa el selector de fotos de Android, que no necesita permisos. |
| _Por definir_ | _Por completar_ |

## Cómo ejecutar

**Requisitos:** Android Studio actualizado y un emulador o teléfono con Android 7.0 o superior.

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/sebaduocccc/proyecto-app-moviles.git
   ```
2. Abrir la carpeta del proyecto en Android Studio.
3. Esperar a que termine la sincronización de Gradle (necesita internet la primera vez).
4. Elegir un emulador o teléfono y presionar **Run ▶**.

**Usuarios de prueba:**

| Rol | Correo | Contraseña |
|---|---|---|
| Administrador | `admin@mastermartini.cl` | `admin` |
| Usuario | `vicentito@gmail.com` | `vicenteDios` |
| Usuario | `srbaes@gmail.com` | `rodri` |

También se puede crear una cuenta nueva desde la pantalla de registro.

> Si los usuarios de prueba no aparecen o tienen datos antiguos, borra los datos de la app (*Ajustes → Apps → Master Martini → Almacenamiento → Borrar datos*) y vuelve a abrirla.

## Planificación y colaboración

- **Tablero Kanban:** https://github.com/users/sebaduocccc/projects/4
- **GitHub:** cada funcionalidad se desarrolló en su propia rama (por ejemplo `feat/12-home-screen` o `feat/06-videos`) y se integró a `main` mediante *pull requests*.
