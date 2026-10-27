package com.mastermartini.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegisterRoute(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: RegisterViewModel = viewModel(factory = RegisterViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.registroExitoso) {
        if (uiState.registroExitoso) {
            onRegisterSuccess()
            viewModel.onRegisterHandled()
        }
    }

    RegisterScreen(
        uiState = uiState,

        onNombreChange = viewModel::onNombreChange,
        onApellidoChange = viewModel::onApellidoChange,
        onUsernameChange = viewModel::onUsernameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onFechaNacimientoChange = viewModel::onFechaNacimientoChange,
        onRegisterClick = viewModel::onRegisterClick,
        onLoginClick = onLoginClick,
        modifier = modifier,
    )
}
