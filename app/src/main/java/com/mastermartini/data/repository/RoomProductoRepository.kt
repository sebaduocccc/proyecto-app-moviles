package com.mastermartini.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mastermartini.data.local.ImageStorage
import com.mastermartini.data.local.ProductoDao
import com.mastermartini.data.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class RoomProductoRepository (
    private val context: Context,
    private val dao: ProductoDao,
    private val imageStorage: ImageStorage,
) : ProductoRepository {
    private val mutex = Mutex()

    override val productos: Flow<List<Producto>> = flow {
        cargarProductosIniciales()
        emitAll(dao.observarTodos())
    }

    override suspend fun getProducto(id: Int): Producto? {
        cargarProductosIniciales()
        return dao.buscarPorId(id)
    }

    override suspend fun crearProducto(producto: Producto): Producto {
        val nuevo = producto.copy(imageUri = imageStorage.importar(producto.imageUri))
        val id = dao.insertar(nuevo).toInt()
        return nuevo.copy(id = id)
    }

    override suspend fun actualizarProducto(producto: Producto) {
        val anterior = dao.buscarPorId(producto.id)
        val nuevo = producto.copy(imageUri = imageStorage.importar(producto.imageUri))
        dao.actualizar(nuevo)

        if (anterior != null && anterior.imageUri != nuevo.imageUri) {
            imageStorage.borrar(anterior.imageUri)
        }
    }

    override suspend fun eliminarProducto(producto: Producto) {
        dao.eliminar(producto)
        imageStorage.borrar(producto.imageUri)
    }

    private suspend fun cargarProductosIniciales() = mutex.withLock {
        if (dao.contar() == 0) {
            dao.insertarTodos(leerProductosDesdeAssets())
        }
    }

    private suspend fun leerProductosDesdeAssets(filename: String = "productos.json"): List<Producto> =
        withContext(Dispatchers.IO) {
            try {
                val json = context.assets.open(filename).bufferedReader().use { it.readText() }
                val tipo = object : TypeToken<List<Producto>>() {}.type
                Gson().fromJson<List<Producto>>(json, tipo) ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
}