package com.mastermartini.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Receta
import com.mastermartini.data.repository.RecetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminRecetasViewModel(
    private val repository: RecetaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AdminRecetasUiState())
    val uiState: StateFlow<AdminRecetasUiState> = _uiState.asStateFlow()
    private var allRecetas: List<Receta> = emptyList()

    init {
        viewModelScope.launch {
            repository.recetas.collect { recetas ->
                allRecetas = recetas

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recetas = filterRecetas(allRecetas, it.query),
                    )
                }
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _uiState.update {
            it.copy(
                query = newQuery,
                recetas = filterRecetas(allRecetas, newQuery),
            )
        }
    }

    fun onEliminarClick(receta: Receta) {
        _uiState.update { it.copy(recetaAEliminar = receta) }
    }

    fun onCancelarEliminar() {
        _uiState.update { it.copy(recetaAEliminar = null) }
    }

    fun onConfirmarEliminar() {
        val receta = _uiState.value.recetaAEliminar ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(recetaAEliminar = null) }
            try {
                repository.eliminarReceta(receta)
                _uiState.update { it.copy(mensaje = "Receta \"${receta.title}\" eliminada") }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensaje = "No se pudo eliminar la receta") }
            }
        }
    }

    fun onMensajeMostrado() {
        _uiState.update { it.copy(mensaje = null) }
    }

    private fun filterRecetas(recetas: List<Receta>, query: String): List<Receta> {
        val texto = query.trim()
        if (texto.isEmpty()) return recetas
        return recetas.filter {
            it.title.contains(texto, ignoreCase = true) ||
                    it.author.contains(texto, ignoreCase = true)
        }
    }
}