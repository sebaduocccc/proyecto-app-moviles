package com.mastermartini.ui.recetadetalle

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mastermartini.MasterMartiniApp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle

val RecetaDetalleViewModelFactory: ViewModelProvider.Factory = viewModelFactory {

    initializer {
        val app = this[APPLICATION_KEY] as MasterMartiniApp

        RecetaDetalleViewModel(
            savedStateHandle = createSavedStateHandle(),
            repository = app.container.recetaRepository,
        )
    }
}