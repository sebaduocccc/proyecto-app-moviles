package com.mastermartini.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdminUsuarioFormRoute(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: AdminUsuarioFormViewModel = viewModel(factory = AdminUsuarioFormViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.guardado) {
        if (uiState.guardado) onClose()
    }

    AdminUsuarioFormScreen(
        uiState = uiState,

        onNombreChange = viewModel::onNombreChange,
        onApellidoChange = viewModel::onApellidoChange,
        onUsernameChange = viewModel::onUsernameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onFechaNacimientoChange = viewModel::onFechaNacimientoChange,
        onRolChange = viewModel::onRolChange,
        onGuardarClick = viewModel::onGuardarClick,
        onBack = onClose,
        modifier = modifier,
    )
}
