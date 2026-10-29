package com.mastermartini.ui.productodetalle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.repository.ProductoRepository
import com.mastermartini.data.repository.RecetaRepository
import com.mastermartini.ui.navigation.ProductoDetalleDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoDetalleViewModel (

    savedStateHandle: SavedStateHandle,

    private val productoRepository: ProductoRepository,

    private val recetaRepository: RecetaRepository,

) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductoDetalleUiState())

    val uiState: StateFlow<ProductoDetalleUiState> = _uiState.asStateFlow()

    private val productoId: Int = checkNotNull(savedStateHandle[ProductoDetalleDestination.ARG_PRODUCTO_ID])

    init {
        viewModelScope.launch {
            val producto = productoRepository.getProducto(productoId)

            val recetas = recetaRepository.getRecetas(producto?.recetaIds.orEmpty())

            _uiState.value = ProductoDetalleUiState(
                isLoading = false,
                producto = producto,
                recetas = recetas,
            )
        }
    }
}