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

object AdminUsuariosDestination {

    const val ROUTE = "admin_usuarios"
}

object AdminUsuarioFormDestination {

    const val ARG_USUARIO_ID = "usuarioId"

    const val NUEVO = -1

    const val ROUTE = "admin_usuario/{$ARG_USUARIO_ID}"

    fun createRoute(id: Int = NUEVO) = "admin_usuario/$id"
}

object AdminHubDestination {
    const val ROUTE = "admin_hub"
}

object AdminRecetasDestination {
    const val ROUTE = "admin_recetas"
}

object AdminRecetaFormDestination {
    const val ARG_RECETA_ID = "recetaId"

    const val NUEVO = -1

    const val ROUTE = "admin_receta/{$ARG_RECETA_ID}"

    fun createRoute(id: Int = NUEVO) = "admin_receta/$id"
}

object AdminProductosDestination {

    const val ROUTE = "admin_productos"
}

object AdminProductoFormDestination {

    const val ARG_PRODUCTO_ID = "productoId"

    const val NUEVO = -1

    const val ROUTE = "admin_producto/{$ARG_PRODUCTO_ID}"

    fun createRoute(id: Int = NUEVO) = "admin_producto/$id"
}

val bottomBarItems = listOf(
    Destination.Home, Destination.Catalogo, Destination.Profile
)