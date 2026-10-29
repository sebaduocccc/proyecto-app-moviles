package com.mastermartini.ui.admin

import com.mastermartini.data.model.Roles
import java.time.LocalDate

data class AdminUsuarioFormUiState(

    val esNuevo: Boolean = true,
    val esUsuarioActual: Boolean = false,

    val nombre: String = "",
    val apellido: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val fechaNacimiento: LocalDate? = null,
    val rol: Roles = Roles.USUARIO,

    val nombreError: String? = null,
    val apellidoError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val fechaNacimientoError: String? = null,

    val errorMessage: String? = null,

    val isLoading: Boolean = false,

    val isSaving: Boolean = false,

    val guardado: Boolean = false,

)
