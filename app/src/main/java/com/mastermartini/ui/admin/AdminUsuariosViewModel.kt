package com.mastermartini.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Usuario
import com.mastermartini.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminUsuariosViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUsuariosUiState())

    val uiState: StateFlow<AdminUsuariosUiState> = _uiState.asStateFlow()

    private var allUsuarios: List<Usuario> = emptyList()

    init {
        viewModelScope.launch {
            val actual = repository.obtenerUsuarioActual()
            _uiState.update { it.copy(usuarioActualId = actual?.id) }
        }

        viewModelScope.launch {
            repository.usuarios.collect { usuarios ->
                allUsuarios = usuarios

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        usuarios = filterUsuarios(allUsuarios, it.query),
                    )
                }
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _uiState.update {
            it.copy(
                query = newQuery,
                usuarios = filterUsuarios(allUsuarios, newQuery),
            )
        }
    }

    fun onEliminarClick(usuario: Usuario) {
        if (usuario.id == _uiState.value.usuarioActualId) {
            _uiState.update { it.copy(mensaje = "No puedes eliminar tu propia cuenta") }
            return
        }
        _uiState.update { it.copy(usuarioAEliminar = usuario) }
    }

    fun onCancelarEliminar() {
        _uiState.update { it.copy(usuarioAEliminar = null) }
    }

    fun onConfirmarEliminar() {
        val usuario = _uiState.value.usuarioAEliminar ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(usuarioAEliminar = null) }
            try {
                repository.eliminarUsuario(usuario)
                _uiState.update { it.copy(mensaje = "Usuario @${usuario.username} eliminado") }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensaje = "No se pudo eliminar el usuario") }
            }
        }
    }

    fun onMensajeMostrado() {
        _uiState.update { it.copy(mensaje = null) }
    }

    private fun filterUsuarios(usuarios: List<Usuario>, query: String): List<Usuario> {
        val texto = query.trim()
        if (texto.isEmpty()) return usuarios
        return usuarios.filter {
            "${it.nombre} ${it.apellido}".contains(texto, ignoreCase = true) ||
                it.username.contains(texto, ignoreCase = true) ||
                it.email.contains(texto, ignoreCase = true)
        }
    }

}
