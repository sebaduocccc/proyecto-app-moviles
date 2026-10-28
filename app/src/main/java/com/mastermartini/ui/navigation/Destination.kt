package com.mastermartini.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    object Home : Destination("home", "Inicio", Icons.Default.Home)
    object Catalogo : Destination("catalogo", "Catálogo", Icons.Default.ShoppingCart)
    object Profile : Destination("profile_screen", "Perfil", Icons.Default.Person)



}

object RecetaDetalleDestination {

    const val ARG_RECETA_ID = "recetaId"

    const val ROUTE = "receta/{$ARG_RECETA_ID}"

    fun createRoute(id: Int) = "receta/$id"
}

object ProductoDetalleDestination {

    const val ARG_PRODUCTO_ID = "productoId"

    const val ROUTE = "producto/{$ARG_PRODUCTO_ID}"

    fun createRoute(id: Int) = "producto/$id"
}

val bottomBarItems = listOf(
    Destination.Home, Destination.Catalogo, Destination.Profile
)