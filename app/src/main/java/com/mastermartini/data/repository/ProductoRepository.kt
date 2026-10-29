package com.mastermartini.data.repository

import com.mastermartini.data.model.Producto
import kotlinx.coroutines.flow.Flow

interface ProductoRepository {
    val productos: Flow<List<Producto>>

    suspend fun getProducto(id: Int): Producto?

    suspend fun crearProducto(producto: Producto): Producto
    suspend fun actualizarProducto(producto: Producto)
    suspend fun eliminarProducto(producto: Producto)
}