package com.mastermartini.ui.productodetalle

import com.mastermartini.data.model.Producto
import com.mastermartini.data.model.Receta

data class ProductoDetalleUiState (

    val isLoading: Boolean = true,

    val producto: Producto? = null,

    val recetas: List<Receta> = emptyList(),
)