package com.mastermartini.ui.recetadetalle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mastermartini.data.repository.RecetaRepository
import com.mastermartini.ui.navigation.RecetaDetalleDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecetaDetalleViewModel(
    savedStateHandle: SavedStateHandle,

    private val repository: RecetaRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecetaDetalleUiState())

    val uiState: StateFlow<RecetaDetalleUiState> = _uiState.asStateFlow()

    private val recetaId: Int = checkNotNull(savedStateHandle[RecetaDetalleDestination.ARG_RECETA_ID])

    init {
        _uiState.value = RecetaDetalleUiState(
            isLoading = false,
            receta = repository.getReceta(recetaId)
        )
    }
}