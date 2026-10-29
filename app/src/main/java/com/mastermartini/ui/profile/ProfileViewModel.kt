package com.mastermartini.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Roles
import com.mastermartini.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val name: String = "",
    val lastName: String = "",
    val username: String = "",
    val imageUri: Uri? = null,
    val esAdmin: Boolean = false,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)

class ProfileViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

//    //Pruebas locales del perfil
//    fun cargarPerfil() {
//        _uiState.update {
//            it.copy(
//                name = if (it.name.isBlank()) "Vicente" else it.name,
//                lastName = if (it.lastName.isBlank()) "Garcia" else it.lastName,
//                username = if (it.username.isBlank()) "vicentitoxd" else it.username,
//                isLoading = false
//            )
//        }
//    }
//
//    fun guardarPerfil(name: String, lastName: String, username: String) {
//        _uiState.update {
//            it.copy(
//                name = name,
//                lastName = lastName,
//                username = username,
//                isSaving = false
//            )
//        }
//    }

    fun cargarPerfil() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val usuario = repository.obtenerUsuarioActual()
                if (usuario == null) {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "No se encontró el usuario")
                    }
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        name = usuario.nombre,
                        lastName = usuario.apellido,
                        username = usuario.username,
                        imageUri = usuario.imageUri?.let { uriStr -> Uri.parse(uriStr) },
                        esAdmin = usuario.role == Roles.ADMIN,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar el perfil"
                    )
                }
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            repository.cerrarSesion()
            _uiState.value = ProfileUiState()
        }
    }

    fun guardarPerfil(name: String, lastName: String, username: String, imageUri: Uri?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            try {
                repository.actualizarPerfil(
                    nombre = name.trim(),
                    apellido = lastName.trim(),
                    username = username.trim().removePrefix("@"),
                    imageUri = imageUri?.toString()
                )

                _uiState.update {
                    it.copy(
                        name = name.trim(),
                        lastName = lastName.trim(),
                        username = username.trim().removePrefix("@"),
                        imageUri = imageUri,
                        isSaving = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = "No se pudieron guardar los cambios"
                    )
                }
            }
        }
    }
}