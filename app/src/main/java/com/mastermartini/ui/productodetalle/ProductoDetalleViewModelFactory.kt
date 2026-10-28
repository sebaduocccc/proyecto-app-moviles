package com.mastermartini.ui.productodetalle

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mastermartini.MasterMartiniApp

val ProductoDetalleViewModelFactory: ViewModelProvider.Factory = viewModelFactory {

    initializer {
        val app = this[APPLICATION_KEY] as MasterMartiniApp

        ProductoDetalleViewModel(
            savedStateHandle = createSavedStateHandle(),
            productoRepository = app.container.productoRepository,
            recetaRepository = app.container.recetaRepository,
        )
    }
}