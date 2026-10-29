package com.mastermartini.ui.admin

import com.mastermartini.data.model.Receta

data class AdminProductoFormUiState(

    val esNuevo: Boolean = true,

    val nombre: String = "",
    val precio: String = "",
    val imageUri: String? = null,
    val recetasDisponibles: List<Receta> = emptyList(),
    val recetaIdsSeleccionadas: Set<Int> = emptySet(),

    val nombreError: String? = null,
    val precioError: String? = null,
    val imagenError: String? = null,

    val errorMessage: String? = null,

    val isLoading: Boolean = true,

    val isSaving: Boolean = false,

    val guardado: Boolean = false,

    )