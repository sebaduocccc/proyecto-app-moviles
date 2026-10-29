package com.mastermartini.ui.admin

import com.mastermartini.data.model.Receta

data class AdminRecetasUiState(
    val isLoading: Boolean = true,
    val recetas: List<Receta> = emptyList(),
    val query: String = "",

    val recetaAEliminar: Receta? = null,

    val mensaje: String? = null,
)