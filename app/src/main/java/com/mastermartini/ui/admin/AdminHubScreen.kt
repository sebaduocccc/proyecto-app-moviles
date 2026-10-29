package com.mastermartini.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.ui.theme.MasterMartiniTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHubScreen(
    onBack: () -> Unit,
    onUsuariosClick: () -> Unit,
    onRecetasClick: () -> Unit,
    onProductosClick: () -> Unit,

    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            AdminHubCard(
                titulo = "Usuarios",
                descripcion = "Crear, editar y eliminar cuentas",
                icono = Icons.Default.People,
                onClick = onUsuariosClick
            )
            AdminHubCard(
                titulo = "Recetas",
                descripcion = "Crear, editar y eliminar recetas",
                icono = Icons.Default.Cake,
                onClick = onRecetasClick
            )
            AdminHubCard(
                titulo = "Productos",
                descripcion = "Crear, editar y eliminar productos",
                icono = Icons.Default.Inventory2,
                onClick = onProductosClick
            )
        }
    }
}

@Composable
private fun AdminHubCard(
    titulo: String,
    descripcion: String,
    icono: ImageVector,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(20.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icono, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminHubPreview() {
    MasterMartiniTheme {
        AdminHubScreen(
            onBack = {},
            onUsuariosClick = {},
            onRecetasClick = {},
            onProductosClick = {},
        )
    }
}