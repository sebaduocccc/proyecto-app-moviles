package com.mastermartini.di

import com.mastermartini.data.repository.FakeRecetaRepository
import com.mastermartini.data.repository.RecetaRepository

class AppContainer {

    val recetaRepository: RecetaRepository = FakeRecetaRepository()

}