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
