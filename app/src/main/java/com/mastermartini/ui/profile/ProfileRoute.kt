package com.mastermartini.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mastermartini.navigation.AppScreens

@Composable
fun ProfileRoute(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        navController = navController,
        name = state.name,
        lastName = state.lastName,
        username = state.username
    )
}

