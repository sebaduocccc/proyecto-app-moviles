package com.mastermartini.ui.catalogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Producto
import com.mastermartini.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CatalogoViewModel (
    private val repository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogoUiState())

    val uiState: StateFlow<CatalogoUiState> = _uiState.asStateFlow()

    private var allProductos: List<Producto> = emptyList()

    init {
        viewModelScope.launch {
            repository.productos.collect { productos ->
                allProductos = productos

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        productos = filterProductos(allProductos, it.query),
                    )
                }
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _uiState.update {
            it.copy(
                query = newQuery,
                productos = filterProductos(allProductos, newQuery),
            )
        }
    }


    private fun filterProductos(productos: List<Producto>, query: String): List<Producto> {
        return productos.filter { it.name.contains(query, ignoreCase = true) }
    }

}