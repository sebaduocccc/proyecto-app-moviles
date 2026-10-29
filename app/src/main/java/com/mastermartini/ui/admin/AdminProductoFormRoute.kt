package com.mastermartini.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdminProductoFormRoute(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: AdminProductoFormViewModel = viewModel(factory = AdminProductoFormViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.guardado) {
        if (uiState.guardado) onClose()
    }

    AdminProductoFormScreen(
        uiState = uiState,

        onNombreChange = viewModel::onNombreChange,
        onPrecioChange = viewModel::onPrecioChange,
        onImagenChange = viewModel::onImagenChange,
        onToggleReceta = viewModel::onToggleReceta,
        onGuardarClick = viewModel::onGuardarClick,
        onBack = onClose,
        modifier = modifier,
    )
}