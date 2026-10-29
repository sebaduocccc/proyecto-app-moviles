package com.mastermartini.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdminProductosRoute(
    onBack: () -> Unit,
    onNuevoProducto: () -> Unit,
    onEditarProducto: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: AdminProductosViewModel = viewModel(factory = AdminProductosViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AdminProductosScreen(
        uiState = uiState,

        onBack = onBack,
        onQueryChange = viewModel::onQueryChange,
        onNuevoProducto = onNuevoProducto,
        onEditarProducto = onEditarProducto,
        onEliminarClick = viewModel::onEliminarClick,
        onConfirmarEliminar = viewModel::onConfirmarEliminar,
        onCancelarEliminar = viewModel::onCancelarEliminar,
        onMensajeMostrado = viewModel::onMensajeMostrado,
        modifier = modifier,
    )
}