package com.mastermartini.ui.login

data class LoginUiState(

    val email: String = "",

    val password: String = "",

    val emailError: String? = null,

    val passwordError: String? = null,

    val errorMessage: String? = null,

    val isLoading: Boolean = false,

    val loginExitoso: Boolean = false,

    val intentosFallidos: Int = 0,

)
