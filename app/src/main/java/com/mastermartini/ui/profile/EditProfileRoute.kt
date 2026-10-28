package com.mastermartini.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun EditProfileRoute(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EditProfileScreen(
        name = state.name,
        lastName = state.lastName,
        username = state.username,
        currentImageUri = state.imageUri,
        onSave = { name, lastName, username, newImageUri ->
            viewModel.guardarPerfil(name, lastName, username, newImageUri)
            navController.popBackStack()
        },
        onBack = { navController.popBackStack() }
    )
}