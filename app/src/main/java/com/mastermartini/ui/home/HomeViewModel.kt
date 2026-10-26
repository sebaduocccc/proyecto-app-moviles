package com.mastermartini.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Receta
import com.mastermartini.data.repository.RecetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repository: RecetaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

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

    fun onCategorySelected(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    private fun filterRecetas(recetas: List<Receta>, query: String): List<Receta> {
        return recetas.filter { it.title.contains(query, ignoreCase = true) }
    }

}