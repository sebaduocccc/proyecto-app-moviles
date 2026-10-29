package com.mastermartini.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.components.FormErrorBanner
import com.mastermartini.components.FormTextField
import com.mastermartini.components.ImagePickerField
import com.mastermartini.components.LoadingButton
import com.mastermartini.data.model.Paso
import com.mastermartini.ui.theme.MasterMartiniTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRecetaFormScreen(
    uiState: AdminRecetaFormUiState,

    onTituloChange: (String) -> Unit,
    onTiempoChange: (String) -> Unit,
    onImagenChange: (String) -> Unit,
    onVideoIdChange: (String) -> Unit,
    onIngredienteChange: (Int, String) -> Unit,
    onAgregarIngrediente: () -> Unit,
    onEliminarIngrediente: (Int) -> Unit,
    onPasoTextoChange: (Int, String) -> Unit,
    onPasoImagenChange: (Int, String?) -> Unit,
    onAgregarPaso: () -> Unit,
    onEliminarPaso: (Int) -> Unit,
    onGuardarClick: () -> Unit,
    onBack: () -> Unit,

    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.esNueva) "Nueva receta" else "Editar receta") },
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
                label = "Elegir imagen de portada",
                error = uiState.imagenError
            )

            Spacer(modifier = Modifier.padding(top = 8.dp))

            FormTextField(
                value = uiState.titulo,
                onValueChange = onTituloChange,
                label = "Título",
                leadingIcon = Icons.Default.Title,
                error = uiState.tituloError
            )

            FormTextField(
                value = uiState.tiempoMin,
                onValueChange = onTiempoChange,
                label = "Tiempo (minutos)",
                leadingIcon = Icons.Default.Schedule,
                error = uiState.tiempoError,
                keyboardType = KeyboardType.Number
            )

            FormTextField(
                value = uiState.videoId,
                onValueChange = onVideoIdChange,
                label = "ID de video de YouTube (opcional)",
                leadingIcon = Icons.Default.VideoLibrary,
                placeholder = "dQw4w9WgXcQ",
                error = uiState.videoError
            )

            SectionTitle("Ingredientes")

            uiState.ingredientes.forEachIndexed { index, ingrediente ->
                FormTextField(
                    value = ingrediente,
                    onValueChange = { onIngredienteChange(index, it) },
                    label = "Ingrediente ${index + 1}",
                    leadingIcon = Icons.Default.Restaurant,
                    trailingIcon = {
                        IconButton(onClick = { onEliminarIngrediente(index) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Quitar ingrediente")
                        }
                    }
                )
            }

            uiState.ingredientesError?.let { ErrorText(it) }

            TextButton(onClick = onAgregarIngrediente) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar ingrediente")
            }

            SectionTitle("Pasos")

            uiState.pasos.forEachIndexed { index, paso ->
                PasoEditor(
                    numero = index + 1,
                    paso = paso,
                    onTextoChange = { onPasoTextoChange(index, it) },
                    onImagenChange = { onPasoImagenChange(index, it) },
                    onImagenQuitar = { onPasoImagenChange(index, null) },
                    onEliminar = { onEliminarPaso(index) },
                )
            }

            uiState.pasosError?.let { ErrorText(it) }

            TextButton(onClick = onAgregarPaso) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agregar paso")
            }

            LoadingButton(
                text = if (uiState.esNueva) "Crear receta" else "Guardar cambios",
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
private fun PasoEditor(
    numero: Int,
    paso: Paso,
    onTextoChange: (String) -> Unit,
    onImagenChange: (String) -> Unit,
    onImagenQuitar: () -> Unit,
    onEliminar: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Paso $numero",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onEliminar) {
                    Icon(Icons.Default.Delete, contentDescription = "Quitar paso")
                }
            }

            OutlinedTextField(
                value = paso.texto,
                onValueChange = onTextoChange,
                label = { Text("Descripción") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            ImagePickerField(
                imageUri = paso.imageUri,
                onImageSelected = onImagenChange,
                onClear = onImagenQuitar,
                label = "Agregar foto (opcional)",
                height = 120.dp
            )
        }
    }
}

@Composable
private fun SectionTitle(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
private fun ErrorText(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun AdminRecetaFormPreview() {
    MasterMartiniTheme {
        AdminRecetaFormScreen(
            uiState = AdminRecetaFormUiState(
                esNueva = false,
                titulo = "Pastel Chocolate",
                tiempoMin = "60",
                ingredientes = listOf("Huevos", "Harina"),
                pasos = listOf(Paso("Mezclar todo"), Paso("Hornear")),
                tituloError = "Mínimo 3 caracteres",
            ),
            onTituloChange = {},
            onTiempoChange = {},
            onImagenChange = {},
            onVideoIdChange = {},
            onIngredienteChange = { _, _ -> },
            onAgregarIngrediente = {},
            onEliminarIngrediente = {},
            onPasoTextoChange = { _, _ -> },
            onPasoImagenChange = { _, _ -> },
            onAgregarPaso = {},
            onEliminarPaso = {},
            onGuardarClick = {},
            onBack = {},
        )
    }
}