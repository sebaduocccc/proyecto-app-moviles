package com.mastermartini.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mastermartini.Screens.LoginScreen
import com.mastermartini.Screens.RegisterScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.loginScreen.route)
    {
        composable(AppScreens.loginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.registerScreen.route) {
            RegisterScreen(navController)
        }
    }
}
