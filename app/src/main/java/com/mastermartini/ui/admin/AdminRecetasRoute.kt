package com.mastermartini.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdminRecetasRoute(
    onBack: () -> Unit,
    onNuevaReceta: () -> Unit,
    onEditarReceta: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: AdminRecetasViewModel = viewModel(factory = AdminRecetasViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AdminRecetasScreen(
        uiState = uiState,

        onBack = onBack,
        onQueryChange = viewModel::onQueryChange,

        onNuevaReceta = onNuevaReceta,
        onEditarReceta = onEditarReceta,

        onEliminarClick = viewModel::onEliminarClick,
        onConfirmarEliminar = viewModel::onConfirmarEliminar,
        onCancelarEliminar = viewModel::onCancelarEliminar,
        onMensajeMostrado = viewModel::onMensajeMostrado,

        modifier = modifier,
    )
}