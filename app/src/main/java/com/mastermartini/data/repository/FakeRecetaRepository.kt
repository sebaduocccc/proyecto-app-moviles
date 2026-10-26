package com.mastermartini.data.repository

import com.mastermartini.R
import com.mastermartini.data.model.Receta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeRecetaRepository : RecetaRepository {

    private val _recetas = MutableStateFlow(
        listOf(
            Receta(1, "Pastel Chocolate", "Master Martini", R.drawable.pastelchocolate),
            Receta(2, "Pastel Crema", "Master Martini", R.drawable.pastelcrema),
            Receta(3, "Donuts", "Master Martini", R.drawable.donuts),
        )
    )

    override val recetas: StateFlow<List<Receta>> = _recetas.asStateFlow()
}