package com.mastermartini.ui.login

import com.mastermartini.data.model.Usuario

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val loginExitoso: Boolean = false,
    val usuarioLogueado: Usuario? = null,
    val intentosFallidos: Int = 0
)