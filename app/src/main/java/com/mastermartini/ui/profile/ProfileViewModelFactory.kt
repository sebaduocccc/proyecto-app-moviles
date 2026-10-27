package com.mastermartini.ui.profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mastermartini.MasterMartiniApp

val ProfileViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
    initializer {
        val app = this[
            ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY
        ] as MasterMartiniApp

        ProfileViewModel(app.container.usuarioRepository)
    }
}
