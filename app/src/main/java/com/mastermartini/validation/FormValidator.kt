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
}
