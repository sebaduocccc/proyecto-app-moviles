package com.mastermartini.Screens

import android.content.Context
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mastermartini.components.ProfileImage
import com.mastermartini.navigation.AppNavigation
import com.mastermartini.navigation.AppScreens
import com.mastermartini.ui.theme.Cyan
import com.mastermartini.ui.theme.Khaki


@Composable
fun ProfileScreen(navController: NavController,
                  name:String,lastName:String,username:String) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        ProfileContent(
            name = name,
            lastName = lastName,
            username = username,
            navController = navController,

            modifier = Modifier.padding(innerPadding)

        )
    }
}

@Composable
fun ProfileContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    name: String,
    lastName: String,
    username: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo de Master Martini",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(56.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileImage()

                Text(
                    text = "$name $lastName",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "@$username",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Button(
                    onClick = {
                        navController.navigate(AppScreens.editProfileScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Editar perfil")
                }
            }
        }

        Text(
            text = "Últimas recetas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

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


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    MasterMartiniTheme {
        ProfileScreen(
            //Nombres solo son placeholders
            name = "Vicente",
            lastName = "Garcia",
            username = "vicentitoxd",
            navController = rememberNavController())

    }
}


