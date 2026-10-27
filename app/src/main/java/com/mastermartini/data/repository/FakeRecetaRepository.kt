package com.mastermartini.data.repository

import com.mastermartini.R
import com.mastermartini.data.model.Paso
import com.mastermartini.data.model.Receta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeRecetaRepository : RecetaRepository {

    private val _recetas = MutableStateFlow(
        listOf(
            Receta(
                1,
                "Pastel Chocolate",
                "Master Martini",
                R.drawable.pastelchocolate,
                60,
                listOf(
                    "200 g de chocolate Master Martini",
                    "Huevos",
                    "Harina",
                    "Azúcar",
                ),
                listOf(
                    Paso("Precalienta el horno"),
                    Paso("Derrite el chocolate"),
                    Paso("Haz la mezcla"),
                    Paso("Calienta la mezcla"),
                    Paso("Come :9")
                )
            ),
            Receta(
                2,
                "Pastel Crema",
                "Master Martini",
                R.drawable.pastelcrema,
                60,
                listOf(
                    "200 g de chocolate Master Martini",
                    "Huevos",
                    "Harina",
                    "Azúcar",
                ),
                listOf(
                    Paso("Precalienta el horno"),
                    Paso("Derrite el chocolate"),
                    Paso("Haz la mezcla"),
                    Paso("Calienta la mezcla"),
                    Paso("Come :9")
                )
            ),
            Receta(
                3,
                "Donuts",
                "Master Martini",
                R.drawable.donuts,
                60,
                listOf(
                    "200 g de chocolate Master Martini",
                    "Huevos",
                    "Harina",
                    "Azúcar",
                ),
                listOf(
                    Paso("Precalienta el horno"),
                    Paso("Derrite el chocolate"),
                    Paso("Haz la mezcla"),
                    Paso("Calienta la mezcla"),
                    Paso("Come :9")
                )
            ),
        )
    )

    override val recetas: StateFlow<List<Receta>> = _recetas.asStateFlow()

    override fun getReceta(id: Int): Receta? {
        return _recetas.value.find { it.id == id }
    }
}