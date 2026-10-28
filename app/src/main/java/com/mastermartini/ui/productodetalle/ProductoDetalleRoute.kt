package com.mastermartini.ui.productodetalle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductoDetalleRoute(
    onClose: () -> Unit,
    onRecetaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: ProductoDetalleViewModel = viewModel(factory = ProductoDetalleViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductoDetalleScreen(
        uiState = uiState,
        onClose = onClose,
        onRecetaClick = onRecetaClick,
        modifier = modifier,
    )
}
