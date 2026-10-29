package com.mastermartini.ui.admin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Producto
import com.mastermartini.data.repository.ProductoRepository
import com.mastermartini.data.repository.RecetaRepository
import com.mastermartini.ui.navigation.AdminProductoFormDestination
import com.mastermartini.validation.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminProductoFormViewModel(
    savedStateHandle: SavedStateHandle,

    private val productoRepository: ProductoRepository,

    private val recetaRepository: RecetaRepository,
) : ViewModel() {

    private val productoId: Int =
        savedStateHandle[AdminProductoFormDestination.ARG_PRODUCTO_ID] ?: AdminProductoFormDestination.NUEVO

    private val esNuevo = productoId == AdminProductoFormDestination.NUEVO

    private val _uiState = MutableStateFlow(AdminProductoFormUiState(esNuevo = esNuevo))

    val uiState: StateFlow<AdminProductoFormUiState> = _uiState.asStateFlow()

    private var productoOriginal: Producto? = null

    init {
        viewModelScope.launch {
            if (!esNuevo) {
                val producto = productoRepository.getProducto(productoId)
                if (producto == null) {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "No se encontró el producto") }
                    return@launch
                }
                productoOriginal = producto

                _uiState.update {
                    it.copy(
                        nombre = producto.name,
                        precio = producto.price.toString(),
                        imageUri = producto.imageUri,
                        recetaIdsSeleccionadas = producto.recetaIds.toSet(),
                    )
                }
            }

            recetaRepository.recetas.collect { recetas ->
                _uiState.update { it.copy(isLoading = false, recetasDisponibles = recetas) }
            }
        }
    }

    fun onNombreChange(valor: String) {
        _uiState.update { it.copy(nombre = valor, nombreError = null, errorMessage = null) }
    }

    fun onPrecioChange(valor: String) {
        _uiState.update { it.copy(precio = valor, precioError = null, errorMessage = null) }
    }

    fun onImagenChange(uri: String) {
        _uiState.update { it.copy(imageUri = uri, imagenError = null, errorMessage = null) }
    }

    fun onToggleReceta(recetaId: Int) {
        _uiState.update { s ->
            val nuevos = if (recetaId in s.recetaIdsSeleccionadas) {
                s.recetaIdsSeleccionadas - recetaId
            } else {
                s.recetaIdsSeleccionadas + recetaId
            }
            s.copy(recetaIdsSeleccionadas = nuevos)
        }
    }

    fun onGuardarClick() {
        val state = _uiState.value

        val validado = state.copy(
            nombreError = FormValidator.validarNombreProducto(state.nombre),
            precioError = FormValidator.validarPrecio(state.precio),
            imagenError = FormValidator.validarImagen(state.imageUri),
        )

        if (validado.tieneErrores()) {
            _uiState.value = validado
            return
        }

        val recetaIds = state.recetasDisponibles
            .map { it.id }
            .filter { it in state.recetaIdsSeleccionadas }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            try {
                val original = productoOriginal
                val nombre = state.nombre.trim()
                val precio = state.precio.trim().toInt()

                if (original == null) {

                    productoRepository.crearProducto(
                        Producto(
                            name = nombre,
                            price = precio,
                            imageUri = state.imageUri,
                            recetaIds = recetaIds,
                        )
                    )
                } else {

                    productoRepository.actualizarProducto(
                        original.copy(
                            name = nombre,
                            price = precio,
                            imageUri = state.imageUri,
                            recetaIds = recetaIds,
                        )
                    )
                }
                _uiState.update { it.copy(isSaving = false, guardado = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, errorMessage = "No se pudo guardar el producto") }
            }
        }
    }

    private fun AdminProductoFormUiState.tieneErrores(): Boolean = listOf(
        nombreError, precioError, imagenError
    ).any { it != null }
}