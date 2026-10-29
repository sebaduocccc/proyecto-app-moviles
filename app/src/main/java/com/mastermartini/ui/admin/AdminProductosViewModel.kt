package com.mastermartini.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Producto
import com.mastermartini.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminProductosViewModel(
    private val repository: ProductoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminProductosUiState())

    val uiState: StateFlow<AdminProductosUiState> = _uiState.asStateFlow()

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

    fun onEliminarClick(producto: Producto) {
        _uiState.update { it.copy(productoAEliminar = producto) }
    }

    fun onCancelarEliminar() {
        _uiState.update { it.copy(productoAEliminar = null) }
    }

    fun onConfirmarEliminar() {
        val producto = _uiState.value.productoAEliminar ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(productoAEliminar = null) }
            try {
                repository.eliminarProducto(producto)
                _uiState.update { it.copy(mensaje = "Producto \"${producto.name}\" eliminado") }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensaje = "No se pudo eliminar el producto") }
            }
        }
    }

    fun onMensajeMostrado() {
        _uiState.update { it.copy(mensaje = null) }
    }

    private fun filterProductos(productos: List<Producto>, query: String): List<Producto> {
        val texto = query.trim()
        if (texto.isEmpty()) return productos
        return productos.filter { it.name.contains(texto, ignoreCase = true) }
    }
}