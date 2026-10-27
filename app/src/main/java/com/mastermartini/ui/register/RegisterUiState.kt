package com.mastermartini.ui.register

import java.time.LocalDate

data class RegisterUiState(

    val nombre: String = "",
    val apellido: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val fechaNacimiento: LocalDate? = null,

    val nombreError: String? = null,
    val apellidoError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val fechaNacimientoError: String? = null,

    val errorMessage: String? = null,

    val isLoading: Boolean = false,

    val registroExitoso: Boolean = false,

)
