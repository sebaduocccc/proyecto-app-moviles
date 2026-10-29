package com.mastermartini.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastermartini.components.FormErrorBanner
import com.mastermartini.components.FormTextField
import com.mastermartini.components.ImagePickerField
import com.mastermartini.components.LoadingButton
import com.mastermartini.data.model.Receta
import com.mastermartini.ui.theme.MasterMartiniTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProductoFormScreen(
    uiState: AdminProductoFormUiState,

    onNombreChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    onImagenChange: (String) -> Unit,
    onToggleReceta: (Int) -> Unit,
    onGuardarClick: () -> Unit,
    onBack: () -> Unit,

    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.esNuevo) "Nuevo producto" else "Editar producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            FormErrorBanner(message = uiState.errorMessage)

            ImagePickerField(
                imageUri = uiState.imageUri,
                onImageSelected = onImagenChange,
                label = "Elegir imagen del producto",
                error = uiState.imagenError
            )

            FormTextField(
                value = uiState.nombre,
                onValueChange = onNombreChange,
                label = "Nombre",
                leadingIcon = Icons.Default.Title,
                error = uiState.nombreError,
                modifier = Modifier.padding(top = 12.dp)
            )

            FormTextField(
                value = uiState.precio,
                onValueChange = onPrecioChange,
                label = "Precio",
                leadingIcon = Icons.Default.AttachMoney,
                error = uiState.precioError,
                keyboardType = KeyboardType.Number
            )

            Text(
                text = "Recetas relacionadas",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "${uiState.recetaIdsSeleccionadas.size} seleccionadas",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (uiState.recetasDisponibles.isEmpty()) {
                Text(
                    text = "Aún no hay recetas. Crea recetas primero para poder relacionarlas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                uiState.recetasDisponibles.forEach { receta ->
                    RecetaCheckRow(
                        receta = receta,
                        seleccionada = receta.id in uiState.recetaIdsSeleccionadas,
                        onToggle = { onToggleReceta(receta.id) }
                    )
                }
            }

            LoadingButton(
                text = if (uiState.esNuevo) "Crear producto" else "Guardar cambios",
                isLoading = uiState.isSaving,
                onClick = onGuardarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun RecetaCheckRow(
    receta: Receta,
    seleccionada: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = seleccionada,
                onValueChange = { onToggle() },
                role = Role.Checkbox
            )
            .padding(vertical = 6.dp)
    ) {
        Checkbox(checked = seleccionada, onCheckedChange = null)

        AsyncImage(
            model = receta.imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            text = receta.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdminProductoFormPreview() {
    MasterMartiniTheme {
        AdminProductoFormScreen(
            uiState = AdminProductoFormUiState(
                esNuevo = false,
                isLoading = false,
                nombre = "Chocolate",
                precio = "1500",
                recetasDisponibles = listOf(
                    Receta(1, "Pastel Chocolate", "Master Martini", null, 60, emptyList(), emptyList()),
                    Receta(2, "Donuts", "Master Martini", null, 45, emptyList(), emptyList()),
                ),
                recetaIdsSeleccionadas = setOf(1),
                nombreError = "Mínimo 2 caracteres",
            ),
            onNombreChange = {},
            onPrecioChange = {},
            onImagenChange = {},
            onToggleReceta = {},
            onGuardarClick = {},
            onBack = {},
        )
    }
}