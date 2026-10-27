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
import androidx.navigation.navArgument
import com.mastermartini.ui.home.HomeRoute
import com.mastermartini.ui.catalogo.CatalogoRoute
import com.mastermartini.ui.recetadetalle.RecetaDetalleRoute

@Composable
fun AppRoot() {
    val nav = rememberNavController()

    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute != RecetaDetalleDestination.ROUTE

    Scaffold(
        bottomBar = { if (showBottomBar) BottomBar(nav) }
    ) { padding ->
        NavHost(
            nav,
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