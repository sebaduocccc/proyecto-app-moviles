package com.mastermartini.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mastermartini.ui.admin.AdminHubScreen
import com.mastermartini.ui.admin.AdminProductoFormRoute
import com.mastermartini.ui.admin.AdminProductosRoute
import com.mastermartini.ui.admin.AdminRecetaFormRoute
import com.mastermartini.ui.admin.AdminRecetasRoute
import com.mastermartini.ui.admin.AdminUsuarioFormRoute
import com.mastermartini.ui.admin.AdminUsuariosRoute
import com.mastermartini.ui.catalogo.CatalogoRoute
import com.mastermartini.ui.home.HomeRoute
import com.mastermartini.ui.login.LoginRoute
import com.mastermartini.ui.productodetalle.ProductoDetalleRoute
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
            navController = nav,
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

            composable(Destination.Catalogo.route) {
                CatalogoRoute(
                    onProductoClick = { id ->
                        nav.navigate(ProductoDetalleDestination.createRoute(id))
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }

            composable(AppScreens.loginScreen.route) {
                LoginRoute(
                    onLoginSuccess = { _ ->
                        nav.navigate(Destination.Home.route) {
                            popUpTo(AppScreens.loginScreen.route) { inclusive = true }
                        }
                    },
                    onRegisterClick = {
                        nav.navigate(AppScreens.registerScreen.route)
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }

            composable(AppScreens.registerScreen.route) {
                RegisterRoute(
                    onRegisterSuccess = {
                        nav.navigate(Destination.Home.route) {
                            popUpTo(AppScreens.loginScreen.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onLoginClick = {
                        nav.popBackStack()
                    },
                    modifier = Modifier.statusBarsPadding()
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
                    },
                    onAdminClick = {
                        nav.navigate(AdminHubDestination.ROUTE)
                    }
                )
            }

            composable(AdminHubDestination.ROUTE) {
                AdminHubScreen(
                    onBack = { nav.popBackStack() },
                    onUsuariosClick = { nav.navigate(AdminUsuariosDestination.ROUTE) },
                    onRecetasClick = { nav.navigate(AdminRecetasDestination.ROUTE) },
                    onProductosClick = { nav.navigate(AdminProductosDestination.ROUTE) }
                )
            }

            composable(AdminRecetasDestination.ROUTE) {
                AdminRecetasRoute(
                    onBack = { nav.popBackStack() },
                    onNuevaReceta = {
                        nav.navigate(AdminRecetaFormDestination.createRoute())
                    },
                    onEditarReceta = { id ->
                        nav.navigate(AdminRecetaFormDestination.createRoute(id))
                    }
                )
            }

            composable(
                route = AdminRecetaFormDestination.ROUTE,
                arguments = listOf(
                    navArgument(AdminRecetaFormDestination.ARG_RECETA_ID) {
                        type = NavType.IntType
                    }
                ),
            ) {
                AdminRecetaFormRoute(
                    onClose = { nav.popBackStack() }
                )
            }

            composable(AdminProductosDestination.ROUTE) {
                AdminProductosRoute(
                    onBack = { nav.popBackStack() },
                    onNuevoProducto = {
                        nav.navigate(AdminProductoFormDestination.createRoute())
                    },
                    onEditarProducto = { id ->
                        nav.navigate(AdminProductoFormDestination.createRoute(id))
                    }
                )
            }

            composable(
                route = AdminProductoFormDestination.ROUTE,
                arguments = listOf(
                    navArgument(AdminProductoFormDestination.ARG_PRODUCTO_ID) {
                        type = NavType.IntType
                    }
                ),
            ) {
                AdminProductoFormRoute(
                    onClose = { nav.popBackStack() }
                )
            }

            composable(AdminUsuariosDestination.ROUTE) {
                AdminUsuariosRoute(
                    onBack = { nav.popBackStack() },
                    onNuevoUsuario = {
                        nav.navigate(AdminUsuarioFormDestination.createRoute())
                    },
                    onEditarUsuario = { id ->
                        nav.navigate(AdminUsuarioFormDestination.createRoute(id))
                    }
                )
            }

            composable(
                route = AdminUsuarioFormDestination.ROUTE,
                arguments = listOf(
                    navArgument(AdminUsuarioFormDestination.ARG_USUARIO_ID) {
                        type = NavType.IntType
                    }
                ),
            ) {
                AdminUsuarioFormRoute(
                    onClose = { nav.popBackStack() }
                )
            }

            composable(AppScreens.editProfileScreen.route) {
                EditProfileRoute(
                    navController = nav,
                    viewModel = profileViewModel
                )
            }

            composable(
                route = ProductoDetalleDestination.ROUTE,
                arguments = listOf(
                    navArgument(ProductoDetalleDestination.ARG_PRODUCTO_ID) {
                        type = NavType.IntType
                    }
                ),
            ) {
                ProductoDetalleRoute(
                    onClose = { nav.popBackStack() },
                    onRecetaClick = { id ->
                        nav.navigate(RecetaDetalleDestination.createRoute(id))
                    }
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
        }
    }
}