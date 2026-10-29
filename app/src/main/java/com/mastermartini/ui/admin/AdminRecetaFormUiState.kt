package com.mastermartini.ui.admin

import com.mastermartini.data.model.Paso

data class AdminRecetaFormUiState(

    val esNueva: Boolean = true,

    val titulo: String = "",
    val tiempoMin: String = "",
    val imageUri: String? = null,
    val videoId: String = "",
    val ingredientes: List<String> = listOf(""),
    val pasos: List<Paso> = listOf(Paso("")),

    val tituloError: String? = null,
    val tiempoError: String? = null,
    val imagenError: String? = null,
    val videoError: String? = null,
    val ingredientesError: String? = null,
    val pasosError: String? = null,

    val errorMessage: String? = null,

    val isLoading: Boolean = false,

    val isSaving: Boolean = false,

    val guardado: Boolean = false,

)