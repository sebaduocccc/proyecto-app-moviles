package com.mastermartini.ui.catalogo

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mastermartini.MasterMartiniApp

val CatalogoViewModelFactory: ViewModelProvider.Factory = viewModelFactory {

    initializer {
        val app = this[APPLICATION_KEY] as MasterMartiniApp

        CatalogoViewModel(app.container.productoRepository)
    }

}