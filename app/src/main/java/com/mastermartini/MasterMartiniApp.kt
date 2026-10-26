package com.mastermartini

import android.app.Application
import com.mastermartini.di.AppContainer

class MasterMartiniApp : Application() {

    val container = AppContainer()

}