package com.mastermartini.ui.home

import com.mastermartini.data.model.Receta

data class HomeUiState(

    val isLoading: Boolean = true,

    val recetas: List<Receta> = emptyList(),

    val query: String = "",

    val selectedCategory: String = "Destacados",

    )