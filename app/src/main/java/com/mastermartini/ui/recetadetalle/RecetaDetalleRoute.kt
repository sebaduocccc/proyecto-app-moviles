package com.mastermartini.ui.recetadetalle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RecetaDetalleRoute(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: RecetaDetalleViewModel = viewModel(factory = RecetaDetalleViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecetaDetalleScreen(
        uiState = uiState,
        onClose = onClose,
        modifier = modifier,
    )
}