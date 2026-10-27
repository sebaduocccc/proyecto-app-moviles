package com.mastermartini.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onRecetaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,

        onQueryChange = viewModel::onQueryChange,
        onCategorySelected = viewModel::onCategorySelected,
        onRecetaClick = onRecetaClick,
        modifier = modifier,
    )
}
