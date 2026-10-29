package com.mastermartini.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mastermartini.data.local.ImageStorage
import com.mastermartini.data.local.RecetaDao
import com.mastermartini.data.model.Receta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class RoomRecetaRepository(
    private val context: Context,
    private val dao: RecetaDao,
    private val imageStorage: ImageStorage,
) : RecetaRepository {
    private val mutex = Mutex()

    override val recetas: Flow<List<Receta>> = flow {
        cargarRecetasIniciales()
        emitAll(dao.observarTodas())
    }

    override suspend fun getReceta(id: Int): Receta? {
        cargarRecetasIniciales()
        return dao.buscarPorId(id)
    }

    override suspend fun getRecetas(ids: List<Int>): List<Receta> {
        if (ids.isEmpty()) return emptyList()
        cargarRecetasIniciales()
        val encontradas = dao.buscarPorIds(ids)
        return ids.mapNotNull { id -> encontradas.find { it.id == id } }
    }

    override suspend fun crearReceta(receta: Receta): Receta {
        val lista = prepararImagenes(receta)
        val id = dao.insertar(lista).toInt()
        return lista.copy(id = id)
    }

    override suspend fun actualizarReceta(receta: Receta) {
        val anterior = dao.buscarPorId(receta.id)
        val nueva = prepararImagenes(receta)
        dao.actualizar(nueva)

        if (anterior != null) {
            val siguenEnUso = imagenesDe(nueva).toSet()
            imagenesDe(anterior)
                .filter { it !in siguenEnUso }
                .forEach { imageStorage.borrar(it) }
        }
    }

    override suspend fun eliminarReceta(receta: Receta) {
        dao.eliminar(receta)
        imagenesDe(receta).forEach { imageStorage.borrar(it) }
    }

    private suspend fun prepararImagenes(receta: Receta): Receta = receta.copy(
        imageUri = imageStorage.importar(receta.imageUri),
        pasos = receta.pasos.map { it.copy(imageUri = imageStorage.importar(it.imageUri)) }
    )

    private fun imagenesDe(receta: Receta): List<String> =
        listOfNotNull(receta.imageUri) + receta.pasos.mapNotNull { it.imageUri }

    private suspend fun cargarRecetasIniciales() = mutex.withLock {
        if (dao.contar() == 0) {
            dao.insertarTodas(leerRecetasDesdeAssets())
        }
    }

    private suspend fun leerRecetasDesdeAssets(filename: String = "recetas.json"): List<Receta> =
        withContext(Dispatchers.IO) {
            try {
                val json = context.assets.open(filename).bufferedReader().use { it.readText() }
                val tipo = object : TypeToken<List<Receta>>() {}.type
                Gson().fromJson<List<Receta>>(json, tipo) ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}