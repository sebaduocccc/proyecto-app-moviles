package com.mastermartini.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileRoute(
    navController: NavController,
    viewModel: ProfileViewModel,
    onLogout: () -> Unit
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
        onLogoutClick = {
            viewModel.cerrarSesion()
            onLogout()
        }
    )
}

