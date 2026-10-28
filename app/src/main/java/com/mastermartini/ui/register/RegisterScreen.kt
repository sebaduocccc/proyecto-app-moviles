package com.mastermartini.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.components.BirthDateField
import com.mastermartini.components.FormErrorBanner
import com.mastermartini.components.FormTextField
import com.mastermartini.components.LoadingButton
import com.mastermartini.components.LogoImage
import com.mastermartini.components.PasswordField
import com.mastermartini.data.model.Roles
import com.mastermartini.ui.theme.MasterMartiniTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onNombreChange: (String) -> Unit,
    onApellidoChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onFechaNacimientoChange: (LocalDate) -> Unit,
    onRolChange: (Roles) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoImage(modifier = Modifier.fillMaxWidth(0.6f))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Completa tus datos para registrarte",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
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
                error = uiState.passwordError,
                imeAction = ImeAction.Done
            )

            BirthDateField(
                value = uiState.fechaNacimiento,
                onDateSelected = onFechaNacimientoChange,
                error = uiState.fechaNacimientoError
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = uiState.rol.displayName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de usuario / Rol") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Roles.entries.forEach { rolOption ->
                        DropdownMenuItem(
                            text = { Text(rolOption.displayName) },
                            onClick = {
                                onRolChange(rolOption)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoadingButton(
            text = "Registrarse",
            isLoading = uiState.isLoading,
            onClick = onRegisterClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("¿Ya tienes cuenta?")
            TextButton(onClick = onLoginClick) {
                Text("Inicia sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    MasterMartiniTheme {
        RegisterScreen(
            uiState = RegisterUiState(
                nombre = "Vicente",
                usernameError = "Este usuario ya existe",
            ),
            onNombreChange = {},
            onApellidoChange = {},
            onUsernameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onFechaNacimientoChange = {},
            onRegisterClick = {},
            onLoginClick = {},
            onRolChange = {},
            modifier = Modifier
        )
    }
}