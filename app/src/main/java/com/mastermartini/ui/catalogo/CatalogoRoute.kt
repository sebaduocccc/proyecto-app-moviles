package com.mastermartini.ui.catalogo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CatalogoRoute(
    onProductoClick: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: CatalogoViewModel = viewModel(factory = CatalogoViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatalogoScreen(
        uiState = uiState,

        onQueryChange = viewModel::onQueryChange,
        onProductoClick = onProductoClick,
        modifier = modifier,
    )
}