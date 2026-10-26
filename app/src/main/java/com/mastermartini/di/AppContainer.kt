package com.mastermartini.di

import com.mastermartini.data.repository.FakeProductoRepository
import com.mastermartini.data.repository.FakeRecetaRepository
import com.mastermartini.data.repository.ProductoRepository
import com.mastermartini.data.repository.RecetaRepository

class AppContainer {

    val recetaRepository: RecetaRepository = FakeRecetaRepository()
    val productoRepository: ProductoRepository = FakeProductoRepository()
}