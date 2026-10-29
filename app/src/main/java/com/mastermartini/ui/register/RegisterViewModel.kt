package com.mastermartini.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Usuario
import com.mastermartini.data.repository.UsuarioRepository
import com.mastermartini.validation.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RegisterViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun onNombreChange(valor: String) {
        _uiState.update { it.copy(nombre = valor, nombreError = null, errorMessage = null) }
    }

    fun onApellidoChange(valor: String) {
        _uiState.update { it.copy(apellido = valor, apellidoError = null, errorMessage = null) }
    }

    fun onUsernameChange(valor: String) {
        _uiState.update { it.copy(username = valor, usernameError = null, errorMessage = null) }
    }

    fun onEmailChange(valor: String) {
        _uiState.update { it.copy(email = valor, emailError = null, errorMessage = null) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor, passwordError = null, errorMessage = null) }
    }

    fun onFechaNacimientoChange(fecha: LocalDate) {
        _uiState.update { it.copy(fechaNacimiento = fecha, fechaNacimientoError = null, errorMessage = null) }
    }

    fun onRegisterClick() {
        val state = _uiState.value
        val validado = state.copy(
            nombreError = FormValidator.validarNombre(state.nombre, "nombre"),
            apellidoError = FormValidator.validarNombre(state.apellido, "apellido"),
            usernameError = FormValidator.validarUsername(state.username),
            emailError = FormValidator.validarEmail(state.email),
            passwordError = FormValidator.validarPasswordRegistro(state.password),
            fechaNacimientoError = FormValidator.validarFechaNacimiento(state.fechaNacimiento)
        )

        if (validado.tieneErrores()) {
            _uiState.value = validado
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val emailError = if (repository.existeEmail(state.email)) "Este correo ya está registrado" else null
            val usernameError = if (repository.existeUsername(state.username)) "Este usuario ya existe" else null

            if (emailError != null || usernameError != null) {
                _uiState.update {
                    it.copy(isLoading = false, emailError = emailError, usernameError = usernameError)
                }
                return@launch
            }

            try {
                repository.registrar(
                    Usuario(
                        nombre = state.nombre.trim(),
                        apellido = state.apellido.trim(),
                        username = state.username.trim(),
                        email = state.email.trim().lowercase(),
                        password = state.password,
                        fechaNacimiento = state.fechaNacimiento!!.format(dateFormatter),
                    )
                )
                _uiState.update { it.copy(isLoading = false, registroExitoso = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "No se pudo crear la cuenta, inténtalo de nuevo")
                }
            }
        }
    }

    fun onRegisterHandled() {
        _uiState.update { RegisterUiState() }
    }

    private fun RegisterUiState.tieneErrores(): Boolean = listOf(
        nombreError, apellidoError, usernameError, emailError, passwordError, fechaNacimientoError
    ).any { it != null }
}