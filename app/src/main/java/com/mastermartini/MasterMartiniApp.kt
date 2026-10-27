package com.mastermartini

import android.app.Application
import com.mastermartini.di.AppContainer

class MasterMartiniApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
