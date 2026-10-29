package com.mastermartini.ui.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AdminUsuariosRoute(
    onBack: () -> Unit,
    onNuevoUsuario: () -> Unit,
    onEditarUsuario: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: AdminUsuariosViewModel = viewModel(factory = AdminUsuariosViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AdminUsuariosScreen(
        uiState = uiState,

        onBack = onBack,
        onQueryChange = viewModel::onQueryChange,
        onNuevoUsuario = onNuevoUsuario,
        onEditarUsuario = onEditarUsuario,
        onEliminarClick = viewModel::onEliminarClick,
        onConfirmarEliminar = viewModel::onConfirmarEliminar,
        onCancelarEliminar = viewModel::onCancelarEliminar,
        onMensajeMostrado = viewModel::onMensajeMostrado,
        modifier = modifier,
    )
}
