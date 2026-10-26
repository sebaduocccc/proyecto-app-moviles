package com.mastermartini.navigation

sealed class AppScreens(val route: String) {


    object loginScreen: AppScreens("login_screen")
    object registerScreen: AppScreens("register_screen")

    object profileScreen: AppScreens("profile_screen")
    object editProfileScreen: AppScreens("edit_profile_screen")
}