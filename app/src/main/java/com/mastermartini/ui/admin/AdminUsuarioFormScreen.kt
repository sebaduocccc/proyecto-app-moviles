package com.mastermartini.ui.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.components.BirthDateField
import com.mastermartini.components.FormErrorBanner
import com.mastermartini.components.FormTextField
import com.mastermartini.components.LoadingButton
import com.mastermartini.components.PasswordField
import com.mastermartini.data.model.Roles
import com.mastermartini.ui.theme.MasterMartiniTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsuarioFormScreen(
    uiState: AdminUsuarioFormUiState,

    onNombreChange: (String) -> Unit,
    onApellidoChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onFechaNacimientoChange: (LocalDate) -> Unit,
    onRolChange: (Roles) -> Unit,
    onGuardarClick: () -> Unit,
    onBack: () -> Unit,

    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.esNuevo) "Nuevo usuario" else "Editar usuario") },
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

            FormTextField(
                value = uiState.nombre,
                onValueChange = onNombreChange,
                label = "Nombre",
                leadingIcon = Icons.Default.Person,
                error = uiState.nombreError
            )

            FormTextField(
                value = uiState.apellido,
                onValueChange = onApellidoChange,
                label = "Apellido",
                leadingIcon = Icons.Default.Badge,
                error = uiState.apellidoError
            )

            FormTextField(
                value = uiState.username,
                onValueChange = onUsernameChange,
                label = "Nombre de usuario",
                leadingIcon = Icons.Default.AlternateEmail,
                error = uiState.usernameError
            )

            FormTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = "Correo electrónico",
                leadingIcon = Icons.Default.Email,
                error = uiState.emailError,
                keyboardType = KeyboardType.Email
            )

            PasswordField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = if (uiState.esNuevo) "Contraseña" else "Nueva contraseña (opcional)",
                error = uiState.passwordError
            )

            BirthDateField(
                value = uiState.fechaNacimiento,
                onDateSelected = onFechaNacimientoChange,
                error = uiState.fechaNacimientoError
            )

            Text(
                text = "Rol",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Roles.entries.forEach { rol ->
                    FilterChip(
                        selected = uiState.rol == rol,
                        onClick = { onRolChange(rol) },
                        enabled = !uiState.esUsuarioActual,
                        label = { Text(rol.displayName) },
                        shape = RoundedCornerShape(50)
                    )
                }
            }
            if (uiState.esUsuarioActual) {
                Text(
                    text = "No puedes cambiar tu propio rol",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            LoadingButton(
                text = if (uiState.esNuevo) "Crear usuario" else "Guardar cambios",
                isLoading = uiState.isSaving,
                onClick = onGuardarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminUsuarioFormPreview() {
    MasterMartiniTheme {
        AdminUsuarioFormScreen(
            uiState = AdminUsuarioFormUiState(
                esNuevo = false,
                nombre = "vicentito",
                emailError = "Este correo ya está registrado",
            ),
            onNombreChange = {},
            onApellidoChange = {},
            onUsernameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onFechaNacimientoChange = {},
            onRolChange = {},
            onGuardarClick = {},
            onBack = {},
        )
    }
}
