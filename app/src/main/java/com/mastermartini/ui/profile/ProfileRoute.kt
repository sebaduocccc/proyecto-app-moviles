package com.mastermartini.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun ProfileRoute(
    navController: NavController,
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onAdminClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.cargarPerfil()
    }

    ProfileScreen(
        navController = navController,
        name = state.name,
        lastName = state.lastName,
        username = state.username,
        imageUri = state.imageUri,
        esAdmin = state.esAdmin,
        esInvitado = state.esInvitado,
        onAdminClick = onAdminClick,
        onLogoutClick = {
            viewModel.cerrarSesion()
            onLogout()
        }
    )
}

