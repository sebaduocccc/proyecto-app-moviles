package com.mastermartini.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdminRecetaFormRoute(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: AdminRecetaFormViewModel = viewModel(factory = AdminRecetaFormViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.guardado) {
        if (uiState.guardado) onClose()
    }

    AdminRecetaFormScreen(
        uiState = uiState,

        onTituloChange = viewModel::onTituloChange,
        onTiempoChange = viewModel::onTiempoChange,
        onImagenChange = viewModel::onImagenChange,
        onVideoIdChange = viewModel::onVideoIdChange,
        onIngredienteChange = viewModel::onIngredienteChange,
        onAgregarIngrediente = viewModel::onAgregarIngrediente,
        onEliminarIngrediente = viewModel::onEliminarIngrediente,
        onPasoTextoChange = viewModel::onPasoTextoChange,
        onPasoImagenChange = viewModel::onPasoImagenChange,
        onAgregarPaso = viewModel::onAgregarPaso,
        onEliminarPaso = viewModel::onEliminarPaso,
        onGuardarClick = viewModel::onGuardarClick,

        onBack = onClose,
        modifier = modifier,
    )
}