package com.mastermartini.ui.admin

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mastermartini.MasterMartiniApp

val AdminUsuariosViewModelFactory: ViewModelProvider.Factory = viewModelFactory {

    initializer {
        val app = this[APPLICATION_KEY] as MasterMartiniApp

        AdminUsuariosViewModel(app.container.usuarioRepository)
    }

}
