package com.mastermartini.ui.catalogo

import com.mastermartini.data.model.Producto

data class CatalogoUiState (

    val isLoading: Boolean = true,

    val productos: List<Producto> = emptyList(),

    val query: String = "",

)