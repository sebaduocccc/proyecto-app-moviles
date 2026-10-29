package com.mastermartini.ui.admin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Roles
import com.mastermartini.data.model.Usuario
import com.mastermartini.data.repository.UsuarioRepository
import com.mastermartini.ui.navigation.AdminUsuarioFormDestination
import com.mastermartini.validation.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AdminUsuarioFormViewModel(
    savedStateHandle: SavedStateHandle,

    private val repository: UsuarioRepository,
) : ViewModel() {

    private val usuarioId: Int =
        savedStateHandle[AdminUsuarioFormDestination.ARG_USUARIO_ID] ?: AdminUsuarioFormDestination.NUEVO

    private val esNuevo = usuarioId == AdminUsuarioFormDestination.NUEVO

    private val _uiState = MutableStateFlow(AdminUsuarioFormUiState(esNuevo = esNuevo, isLoading = !esNuevo))

    val uiState: StateFlow<AdminUsuarioFormUiState> = _uiState.asStateFlow()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private var usuarioOriginal: Usuario? = null

    init {
        if (!esNuevo) cargarUsuario()
    }

    private fun cargarUsuario() {
        viewModelScope.launch {
            val usuario = repository.obtenerPorId(usuarioId)
            if (usuario == null) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "No se encontró el usuario") }
                return@launch
            }
            usuarioOriginal = usuario
            val actual = repository.obtenerUsuarioActual()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    esUsuarioActual = actual?.id == usuario.id,
                    nombre = usuario.nombre,
                    apellido = usuario.apellido,
                    username = usuario.username,
                    email = usuario.email,
                    fechaNacimiento = parseFecha(usuario.fechaNacimiento),
                    rol = usuario.role,
                )
            }
        }
    }

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

    fun onRolChange(rol: Roles) {
        if (_uiState.value.esUsuarioActual) return
        _uiState.update { it.copy(rol = rol) }
    }

    fun onGuardarClick() {
        val state = _uiState.value
        val passwordError = if (!esNuevo && state.password.isEmpty()) null
        else FormValidator.validarPasswordRegistro(state.password)

        val validado = state.copy(
            nombreError = FormValidator.validarNombre(state.nombre, "nombre"),
            apellidoError = FormValidator.validarNombre(state.apellido, "apellido"),
            usernameError = FormValidator.validarUsername(state.username),
            emailError = FormValidator.validarEmail(state.email),
            passwordError = passwordError,
            fechaNacimientoError = FormValidator.validarFechaNacimiento(state.fechaNacimiento),
        )

        if (validado.tieneErrores()) {
            _uiState.value = validado
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            val excluirId = usuarioOriginal?.id
            val emailError = if (repository.emailEnUso(state.email, excluirId)) "Este correo ya está registrado" else null
            val usernameError = if (repository.usernameEnUso(state.username, excluirId)) "Este usuario ya existe" else null

            if (emailError != null || usernameError != null) {
                _uiState.update { it.copy(isSaving = false, emailError = emailError, usernameError = usernameError) }
                return@launch
            }

            try {
                val original = usuarioOriginal
                if (original == null) {
                    repository.crearUsuario(
                        Usuario(
                            nombre = state.nombre.trim(),
                            apellido = state.apellido.trim(),
                            username = state.username.trim(),
                            email = state.email.trim().lowercase(),
                            password = state.password,
                            fechaNacimiento = state.fechaNacimiento!!.format(dateFormatter),
                            role = state.rol,
                        )
                    )
                } else {
                    repository.actualizarUsuario(
                        original.copy(
                            nombre = state.nombre.trim(),
                            apellido = state.apellido.trim(),
                            username = state.username.trim(),
                            email = state.email.trim().lowercase(),
                            password = state.password.ifEmpty { original.password },
                            fechaNacimiento = state.fechaNacimiento!!.format(dateFormatter),
                            role = state.rol,
                        )
                    )
                }
                _uiState.update { it.copy(isSaving = false, guardado = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, errorMessage = "No se pudo guardar el usuario") }
            }
        }
    }

    private fun parseFecha(texto: String): LocalDate? =
        try {
            LocalDate.parse(texto, dateFormatter)
        } catch (e: Exception) {
            null
        }

    private fun AdminUsuarioFormUiState.tieneErrores(): Boolean = listOf(
        nombreError, apellidoError, usernameError, emailError, passwordError, fechaNacimientoError
    ).any { it != null }

}
