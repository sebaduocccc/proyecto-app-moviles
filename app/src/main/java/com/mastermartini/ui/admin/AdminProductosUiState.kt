package com.mastermartini.ui.admin

import com.mastermartini.data.model.Producto

data class AdminProductosUiState(

    val isLoading: Boolean = true,

    val productos: List<Producto> = emptyList(),

    val query: String = "",

    val productoAEliminar: Producto? = null,

    val mensaje: String? = null,
)