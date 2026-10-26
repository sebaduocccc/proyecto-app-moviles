package com.mastermartini.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastermartini.R
import com.mastermartini.ui.theme.MasterMartiniTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color


@Composable
fun ProfileScreen(
    name: String,
    lastName: String,
    username: String
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        ProfileContent(
            name = name,
            lastName = lastName,
            username = username,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ProfileContent(
    name: String,
    lastName: String,
    username: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {Text(
        text = "Mi perfil",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    ) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage()

        Text(
            text = "$name $lastName",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "@$username",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .height(40.dp)
        ){
            Text("Editar perfil")
        }

    }
    Column(modifier = Modifier.fillMaxWidth().padding(top=320.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Ultimas Recetas")
    }


}

@Composable
fun ProfileImage(modifier: Modifier=Modifier){
    //imagen solo es placeholder
    val image = painterResource(R.drawable.profile)
    Column(
        modifier = Modifier.size(128.dp)
            .clip(CircleShape)) {
        Image(
            painter = image,
            contentDescription = null

        )
    }

}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    MasterMartiniTheme {
        ProfileScreen(
            //Nombres solo son placeholders
            name = "Vicente",
            lastName = "Garcia",
            username = "vicentitoxd"
        )
    }
}

