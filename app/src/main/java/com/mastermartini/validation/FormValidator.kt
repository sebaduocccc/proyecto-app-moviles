package com.mastermartini.validation

import java.time.LocalDate
import java.time.Period

object FormValidator {

    const val EDAD_MINIMA = 13

    private val nombreRegex = Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñÜü ]+$")
    private val usernameRegex = Regex("^[A-Za-z0-9_.]+$")
    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validarNombre(valor: String, campo: String): String? {
        val texto = valor.trim()
        return when {
            texto.isEmpty() -> "Ingresa tu $campo"
            texto.length < 2 -> "Mínimo 2 caracteres"
            !nombreRegex.matches(texto) -> "Solo se permiten letras"
            else -> null
        }
    }

    fun validarUsername(valor: String): String? {
        val texto = valor.trim()
        return when {
            texto.isEmpty() -> "Ingresa un nombre de usuario"
            texto.length !in 3..20 -> "Debe tener entre 3 y 20 caracteres"
            !usernameRegex.matches(texto) -> "Solo letras, números, punto y guion bajo"
            else -> null
        }
    }

    fun validarEmail(valor: String): String? {
        val texto = valor.trim()
        return when {
            texto.isEmpty() -> "Ingresa tu correo electrónico"
            !emailRegex.matches(texto) -> "Correo electrónico no válido"
            else -> null
        }
    }

    fun validarPasswordLogin(valor: String): String? =
        if (valor.isEmpty()) "Ingresa tu contraseña" else null

    fun validarPasswordRegistro(valor: String): String? = when {
        valor.isEmpty() -> "Ingresa una contraseña"
        valor.length < 6 -> "Mínimo 6 caracteres"
        valor.none { it.isLetter() } || valor.none { it.isDigit() } -> "Debe tener al menos una letra y un número"
        else -> null
    }

    fun validarFechaNacimiento(fecha: LocalDate?, hoy: LocalDate = LocalDate.now()): String? = when {
        fecha == null -> "Selecciona tu fecha de nacimiento"
        fecha.isAfter(hoy) -> "La fecha no puede ser futura"
        Period.between(fecha, hoy).years < EDAD_MINIMA -> "Debes tener al menos $EDAD_MINIMA años"
        else -> null
    }

    private val videoIdRegex = Regex("^[A-Za-z0-9_-]{11}$")

    fun validarTituloReceta(valor: String): String? {
        val texto = valor.trim()
        return when {
            texto.isEmpty() -> "Ingresa un título"
            texto.length < 3 -> "Mínimo 3 caracteres"
            texto.length > 60 -> "Máximo 60 caracteres"
            else -> null
        }
    }

    fun validarTiempoMin(valor: String): String? {
        val minutos = valor.trim().toIntOrNull()
        return when {
            valor.isBlank() -> "Ingresa el tiempo de preparación"
            minutos == null -> "Solo números"
            minutos !in 1..1440 -> "Debe estar entre 1 y 1440 minutos"
            else -> null
        }
    }

    fun validarImagen(uri: String?): String? =
        if (uri.isNullOrBlank()) "Selecciona una imagen" else null

    fun validarVideoId(valor: String): String? {
        val texto = valor.trim()
        return if (texto.isEmpty() || videoIdRegex.matches(texto)) null
        else "ID de YouTube no válido"
    }

    fun validarIngredientes(lista: List<String>): String? =
        if (lista.isEmpty()) "Agrega al menos un ingrediente" else null

    fun validarPasos(lista: List<*>): String? =
        if (lista.isEmpty()) "Agrega al menos un paso" else null

    fun validarPrecio(valor: String): String? {
        val precio = valor.trim().toIntOrNull()
        return when {
            valor.isBlank() -> "Ingresa un precio"
            precio == null -> "Solo números enteros"
            precio !in 0..10_000_000 -> "Debe estar entre 0 y 10.000.000"
            else -> null
        }
    }

    fun validarNombreProducto(valor: String): String? {
        val texto = valor.trim()
        return when {
            texto.isEmpty() -> "Ingresa un nombre"
            texto.length < 2 -> "Mínimo 2 caracteres"
            texto.length > 40 -> "Máximo 40 caracteres"
            else -> null
        }
    }
}
