package com.mastermartini.data.repository

import com.mastermartini.R
import com.mastermartini.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeProductoRepository : ProductoRepository {

    private val _productos = MutableStateFlow(
        listOf(
            Producto(1, "Chocolate", 0, R.drawable.pastelchocolate),
            Producto(2, "Crema", 0, R.drawable.pastelcrema),
            Producto(3, "Glaseado", 0, R.drawable.donuts),
        )
    )

    override val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    override fun getProducto(id: Int): Producto? {
        return _productos.value.find { it.id == id }
    }
}