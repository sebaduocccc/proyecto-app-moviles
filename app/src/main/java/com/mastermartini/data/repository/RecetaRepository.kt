package com.mastermartini.data.repository

import com.mastermartini.data.model.Receta
import kotlinx.coroutines.flow.Flow

interface RecetaRepository {

    val recetas: Flow<List<Receta>>

    suspend fun getReceta(id: Int): Receta?

    suspend fun getRecetas(ids: List<Int>): List<Receta>

    suspend fun crearReceta(receta: Receta): Receta
    suspend fun actualizarReceta(receta: Receta)
    suspend fun eliminarReceta(receta: Receta)
}