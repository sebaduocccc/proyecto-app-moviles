package com.mastermartini.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastermartini.components.ProfileImage
import com.mastermartini.ui.theme.MasterMartiniTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    name: String,
    lastName: String,
    username: String,
    currentImageUri: Uri? = null,
    onSave: (name: String, lastName: String, username: String, imageUri: Uri?) -> Unit,
    onBack: () -> Unit
) {
    var editedName by remember { mutableStateOf(name) }
    var editedLastName by remember { mutableStateOf(lastName) }
    var editedUsername by remember { mutableStateOf(username) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(currentImageUri) }

    // Actualiza los campos locales solo la primera vez que la pantalla recibe datos del ViewModel
    LaunchedEffect(Unit) {
        if (editedName.isEmpty()) editedName = name
        if (editedLastName.isEmpty()) editedLastName = lastName
        if (editedUsername.isEmpty()) editedUsername = username
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

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
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Foto de perfil seleccionada",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                ProfileImage()
            }

            TextButton(
                onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            ) {
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
                        editedUsername.trim(),
                        selectedImageUri
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