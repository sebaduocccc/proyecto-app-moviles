package com.mastermartini.data.model

import java.time.LocalDate

data class Usuario(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val username: String,
    val email: String,
    val password: String,
    val fechaNacimiento: LocalDate
)
