package com.mastermartini.ui.recetadetalle

import com.mastermartini.data.model.Receta

data class RecetaDetalleUiState(

    val isLoading: Boolean = true,

    val receta: Receta? = null,

)