package com.mastermartini.data.repository

import com.mastermartini.data.model.Producto
import kotlinx.coroutines.flow.StateFlow

interface ProductoRepository {
    val productos: StateFlow<List<Producto>>

    fun getProducto(id: Int): Producto?
}