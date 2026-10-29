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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.data.model.Roles
import com.mastermartini.data.model.Usuario
import com.mastermartini.ui.theme.MasterMartiniTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsuariosScreen(
    uiState: AdminUsuariosUiState,

    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onNuevoUsuario: () -> Unit,
    onEditarUsuario: (Int) -> Unit,
    onEliminarClick: (Usuario) -> Unit,
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
                title = { Text("Panel de administración") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNuevoUsuario,
                icon = { Icon(Icons.Default.PersonAdd, contentDescription = null) },
                text = { Text("Nuevo usuario") }
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
                placeholder = { Text("Buscar por nombre, usuario o correo") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )

            Text(
                text = "${uiState.usuarios.size} usuarios",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            when {
                uiState.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

                uiState.usuarios.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron usuarios", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                else -> LazyColumn(
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.usuarios, key = { it.id }) { usuario ->
                        UsuarioCard(
                            usuario = usuario,
                            esUsuarioActual = usuario.id == uiState.usuarioActualId,
                            onEditar = { onEditarUsuario(usuario.id) },
                            onEliminar = { onEliminarClick(usuario) },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }

    uiState.usuarioAEliminar?.let { usuario ->
        AlertDialog(
            onDismissRequest = onCancelarEliminar,
            icon = { Icon(Icons.Default.Delete, contentDescription = null) },
            title = { Text("Eliminar usuario") },
            text = { Text("¿Seguro que quieres eliminar a @${usuario.username}? Esta acción no se puede deshacer.") },
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
fun UsuarioCard(
    usuario: Usuario,
    esUsuarioActual: Boolean,
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
            modifier = Modifier.padding(16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = usuario.nombre.take(1).uppercase(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${usuario.nombre} ${usuario.apellido}" + if (esUsuarioActual) " (tú)" else "",
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "@${usuario.username} · ${usuario.email}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                RolBadge(rol = usuario.role, modifier = Modifier.padding(top = 4.dp))
            }

            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar usuario", tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onEliminar, enabled = !esUsuarioActual) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar usuario")
            }
        }
    }
}

@Composable
fun RolBadge(rol: Roles, modifier: Modifier = Modifier) {
    val esAdmin = rol == Roles.ADMIN
    Surface(
        shape = RoundedCornerShape(50),
        color = if (esAdmin) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (esAdmin) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    ) {
        Text(
            text = rol.displayName,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdminUsuariosPreview() {
    MasterMartiniTheme {
        AdminUsuariosScreen(
            uiState = AdminUsuariosUiState(
                isLoading = false,
                usuarioActualId = 1,
                usuarios = listOf(
                    Usuario(1, "admin", "garcia", "admin", "admin@mastermartini.cl", "admin", "01/01/2001", Roles.ADMIN),
                    Usuario(2, "vicentito", "garcia", "vixo01", "vicentito@gmail.com", "x", "17/07/2000"),
                ),
            ),
            onBack = {},
            onQueryChange = {},
            onNuevoUsuario = {},
            onEditarUsuario = {},
            onEliminarClick = {},
            onConfirmarEliminar = {},
            onCancelarEliminar = {},
            onMensajeMostrado = {},
        )
    }
}
