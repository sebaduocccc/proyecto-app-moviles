package com.mastermartini.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastermartini.data.model.Paso
import com.mastermartini.data.model.Receta
import com.mastermartini.ui.theme.MasterMartiniTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRecetasScreen(
    uiState: AdminRecetasUiState,

    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onNuevaReceta: () -> Unit,
    onEditarReceta: (Int) -> Unit,
    onEliminarClick: (Receta) -> Unit,
    onConfirmarEliminar: () -> Unit,
    onCancelarEliminar: () -> Unit,
    onMensajeMostrado: () -> Unit,

    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let {
            snackbarHostState.showSnackbar(it)
            onMensajeMostrado()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Recetas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNuevaReceta,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Nueva receta") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = onQueryChange,
                placeholder = { Text("Buscar por título o autor") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )

            Text(
                text = "${uiState.recetas.size} recetas",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            when {
                uiState.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                uiState.recetas.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron recetas", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                else -> LazyColumn(
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.recetas, key = { it.id }) { receta ->
                        RecetaAdminCard(
                            receta = receta,
                            onEditar = { onEditarReceta(receta.id) },
                            onEliminar = { onEliminarClick(receta) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }

    uiState.recetaAEliminar?.let { receta ->
        AlertDialog(
            onDismissRequest = onCancelarEliminar,
            icon = { Icon(Icons.Default.Delete, contentDescription = null) },
            title = { Text("Eliminar receta") },
            text = { Text("¿Seguro que quieres eliminar \"${receta.title}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = onConfirmarEliminar) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = onCancelarEliminar) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun RecetaAdminCard(
    receta: Receta,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = receta.imageUri,
                contentDescription = receta.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = receta.title,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${receta.tiempoMin} min · ${receta.pasos.size} pasos",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar receta", tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar receta")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminRecetasPreview() {
    MasterMartiniTheme {
        AdminRecetasScreen(
            uiState = AdminRecetasUiState(
                isLoading = false,
                recetas = listOf(
                    Receta(1, "Pastel Chocolate", "Master Martini", null, 60, listOf("Huevos"), listOf(Paso("Mezclar"))),
                    Receta(2, "Donuts", "Master Martini", null, 45, listOf("Harina"), listOf(Paso("Hornear"), Paso("Glasear"))),
                ),
            ),
            onBack = {},
            onQueryChange = {},
            onNuevaReceta = {},
            onEditarReceta = {},
            onEliminarClick = {},
            onConfirmarEliminar = {},
            onCancelarEliminar = {},
            onMensajeMostrado = {},
        )
    }
}