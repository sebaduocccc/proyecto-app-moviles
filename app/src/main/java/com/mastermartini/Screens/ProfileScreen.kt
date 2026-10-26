package com.mastermartini.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mastermartini.ui.theme.Cyan
import com.mastermartini.ui.theme.Khaki


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
    Column (
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo de Master Martini",
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Mi Perfil",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 100.dp),
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
    Column(modifier = Modifier.fillMaxWidth().padding(top=340.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Ultimas Recetas")
        Spacer(modifier = Modifier.height(8.dp))
        RecetaCard()
    }





}
@Composable
fun RecetaCard() {

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.pastelchocolate),
                contentDescription = "Pastel Chocolate",
                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pastel De Chocolate",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Hecho por Vicente",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
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

