package com.mastermartini.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    object Home : Destination("home", "Inicio", Icons.Default.Home)
    object Catalogo : Destination("catalogo", "Catálogo", Icons.Default.ShoppingCart)
}

val bottomBarItems = listOf(
    Destination.Home, Destination.Catalogo
)