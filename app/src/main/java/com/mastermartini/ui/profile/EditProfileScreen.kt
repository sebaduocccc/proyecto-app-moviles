package com.mastermartini.ui.profile
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.ui.theme.MasterMartiniTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import com.mastermartini.components.ProfileImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    name: String,
    lastName: String,
    username: String,
    onSave: (name: String, lastName: String, username: String) -> Unit,
    onBack: () -> Unit
) {
    var editedName by remember(name) { mutableStateOf(name) }
    var editedLastName by remember(lastName) { mutableStateOf(lastName) }
    var editedUsername by remember(username) { mutableStateOf(username) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar perfil") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Cancelar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileImage()

            TextButton(onClick = { /* Abrir selector de imagen */ }) {
                Text("Cambiar foto")
            }

            OutlinedTextField(
                value = editedName,
                onValueChange = { editedName = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = editedLastName,
                onValueChange = { editedLastName = it },
                label = { Text("Apellido") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = editedUsername,
                onValueChange = { editedUsername = it.removePrefix("@") },
                label = { Text("Nombre de usuario") },
                prefix = { Text("@") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    onSave(
                        editedName.trim(),
                        editedLastName.trim(),
                        editedUsername.trim()
                    )

                },
                enabled = editedName.isNotBlank() &&
                        editedLastName.isNotBlank() &&
                        editedUsername.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    MasterMartiniTheme {
        EditProfileScreen(
            name = "Vicente",
            lastName = "Garcia",
            username = "vicentitoxd",
            onSave = { _, _, _ -> },
            onBack = {}
        )
    }
}

