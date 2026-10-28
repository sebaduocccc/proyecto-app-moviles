package com.mastermartini.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mastermartini.ui.catalogo.CatalogoRoute
import com.mastermartini.ui.home.HomeRoute
import com.mastermartini.ui.login.LoginRoute
import com.mastermartini.ui.profile.EditProfileRoute
import com.mastermartini.ui.profile.ProfileRoute
import com.mastermartini.ui.profile.ProfileViewModel
import com.mastermartini.ui.profile.ProfileViewModelFactory
import com.mastermartini.ui.recetadetalle.RecetaDetalleRoute
import com.mastermartini.ui.register.RegisterRoute

@Composable
fun AppRoot() {
    val nav = rememberNavController()
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = bottomBarItems.any { it.route == currentRoute }
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory
    )


    Scaffold(
        bottomBar = { if (showBottomBar) BottomBar(nav) }
    ) { padding ->
        NavHost(
            nav,
            startDestination = AppScreens.loginScreen.route,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            composable(Destination.Home.route) {
                HomeRoute(
                    onRecetaClick = { id ->
                        nav.navigate(RecetaDetalleDestination.createRoute(id))
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }

            composable(Destination.Catalogo.route) { CatalogoRoute(modifier = Modifier.statusBarsPadding()) }

            composable(AppScreens.loginScreen.route) {
                LoginRoute(
                    onLoginSuccess = {
                        nav.navigate(Destination.Home.route) {
                            popUpTo(AppScreens.loginScreen.route) { inclusive = true }
                        }
                    },
                    onRegisterClick = { nav.navigate(AppScreens.registerScreen.route) },
                    modifier = Modifier.statusBarsPadding()
                )
            }
            composable(AppScreens.registerScreen.route) {
                RegisterRoute(
                    onRegisterSuccess = {
                        nav.navigate(Destination.Home.route) {
                            popUpTo(AppScreens.loginScreen.route) { inclusive = true }
                        }
                    },
                    onLoginClick = { nav.popBackStack() },
                    modifier = Modifier.statusBarsPadding()
                )
            }


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
            composable(Destination.Profile.route) {
                ProfileRoute(
                    navController = nav,
                    viewModel = profileViewModel,
                    onLogout = {
                        nav.navigate(AppScreens.loginScreen.route) {
                            popUpTo(nav.graph.id) { inclusive = true }
                        }
                    }
                )
            }

            composable(AppScreens.editProfileScreen.route) {
                EditProfileRoute(
                    navController = nav,
                    viewModel = profileViewModel
                )
            }
        }
    }
}
