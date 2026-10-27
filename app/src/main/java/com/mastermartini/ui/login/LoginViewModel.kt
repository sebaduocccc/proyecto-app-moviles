package com.mastermartini.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.repository.UsuarioRepository
import com.mastermartini.validation.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null, errorMessage = null) }
    }

    fun onLoginClick() {
        val state = _uiState.value
        val emailError = FormValidator.validarEmail(state.email)
        val passwordError = FormValidator.validarPasswordLogin(state.password)

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError,
                    intentosFallidos = it.intentosFallidos + 1,
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val usuario = repository.login(state.email, state.password)

            _uiState.update {
                if (usuario != null) {
                    it.copy(isLoading = false, loginExitoso = true)
                } else {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Correo o contraseña incorrectos",
                        intentosFallidos = it.intentosFallidos + 1,
                    )
                }
            }
        }
    }

    fun onLoginHandled() {
        _uiState.update { LoginUiState() }
    }

}
