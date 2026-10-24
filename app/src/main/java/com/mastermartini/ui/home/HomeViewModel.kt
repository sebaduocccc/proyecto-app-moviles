package com.mastermartini.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

data class HomeUiState (
    val isLoading: Boolean = true,
    val items: List<String> = emptyList(),
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _uiState.update {
                state -> state.copy(isLoading = true )
            }
            delay(1000.milliseconds)
            _uiState.update {
                state -> state.copy(isLoading = false, items = List(5) { i -> "Item ${i + 1}"})
            }
        }
    }
}