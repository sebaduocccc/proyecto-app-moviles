package com.mastermartini.data.repository

import com.mastermartini.data.model.Receta
import kotlinx.coroutines.flow.StateFlow

interface RecetaRepository {

    val recetas: StateFlow<List<Receta>>

}