package com.mastermartini.navigation

sealed class AppScreens(val route: String) {


    object loginScreen: AppScreens("login_screen")
    object registerScreen: AppScreens("register_screen")
}