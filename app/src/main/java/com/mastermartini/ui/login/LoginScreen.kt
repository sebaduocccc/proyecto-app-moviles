package com.mastermartini.ui.login

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.components.FormErrorBanner
import com.mastermartini.components.FormTextField
import com.mastermartini.components.LoadingButton
import com.mastermartini.components.LogoImage
import com.mastermartini.components.PasswordField
import com.mastermartini.ui.theme.MasterMartiniTheme

@Composable
fun LoginScreen(
    uiState: LoginUiState,

    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,

    modifier: Modifier = Modifier
) {
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(uiState.intentosFallidos) {
        if (uiState.intentosFallidos > 0) {
            for (offset in listOf(-12f, 12f, -8f, 8f, -4f, 4f, 0f)) {
                shakeOffset.animateTo(offset, tween(durationMillis = 40))
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoImage()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Ingresa con tu correo y contraseña",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer { translationX = shakeOffset.value.dp.toPx() }
        ) {
            FormErrorBanner(message = uiState.errorMessage)

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
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(onDone = { onLoginClick() })
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoadingButton(
            text = "Iniciar sesión",
            isLoading = uiState.isLoading,
            onClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("¿No tienes cuenta?")
            TextButton(onClick = onRegisterClick) {
                Text("Regístrate")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MasterMartiniTheme {
        LoginScreen(
            uiState = LoginUiState(
                email = "correo-invalido",
                emailError = "Correo electrónico no válido",
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {},
        )
    }
}
