package com.mastermartini.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
<<<<<<< HEAD
import androidx.navigation.navArgument
import com.mastermartini.ui.home.HomeRoute
import com.mastermartini.ui.catalogo.CatalogoRoute
import com.mastermartini.ui.recetadetalle.RecetaDetalleRoute
=======
import com.mastermartini.navigation.AppScreens
import com.mastermartini.ui.home.HomeRoute
import com.mastermartini.ui.catalogo.CatalogoRoute
import com.mastermartini.ui.login.LoginRoute
import com.mastermartini.ui.register.RegisterRoute
>>>>>>> origin/loginScreen

@Composable
fun AppRoot() {
    val nav = rememberNavController()
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = bottomBarItems.any { it.route == currentRoute }

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute != RecetaDetalleDestination.ROUTE

    Scaffold(
        bottomBar = { if (showBottomBar) BottomBar(nav) }
    ) { padding ->
        NavHost(
            nav,
<<<<<<< HEAD
            startDestination = "home",
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            composable(Destination.Home.route) {
                HomeRoute(
                    onRecetaClick = { id ->
                        nav.navigate(RecetaDetalleDestination.createRoute(id))
                    }
                )
            }

=======
            startDestination = AppScreens.loginScreen.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(AppScreens.loginScreen.route) {
                LoginRoute(
                    onLoginSuccess = {
                        nav.navigate(Destination.Home.route) {
                            popUpTo(AppScreens.loginScreen.route) { inclusive = true }
                        }
                    },
                    onRegisterClick = { nav.navigate(AppScreens.registerScreen.route) }
                )
            }
            composable(AppScreens.registerScreen.route) {
                RegisterRoute(
                    onRegisterSuccess = {
                        nav.navigate(Destination.Home.route) {
                            popUpTo(AppScreens.loginScreen.route) { inclusive = true }
                        }
                    },
                    onLoginClick = { nav.popBackStack() }
                )
            }
            composable(Destination.Home.route) { HomeRoute() }
>>>>>>> origin/loginScreen
            composable(Destination.Catalogo.route) { CatalogoRoute() }

            composable(
                route = RecetaDetalleDestination.ROUTE,
                arguments = listOf(
                    navArgument(RecetaDetalleDestination.ARG_RECETA_ID) {
                        type = NavType.IntType
                    }
                ),
            ) {
                RecetaDetalleRoute(
                    onClose = { nav.popBackStack() }
                )
            }
        }
    }
}
