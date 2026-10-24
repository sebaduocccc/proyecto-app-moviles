package com.mastermartini.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mastermartini.ui.catalogo.CatalogoScreen
import com.mastermartini.ui.home.HomeScreen

@Composable
fun AppRoot() {
    val nav = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(nav) }
    ) { padding ->
        NavHost(
            nav,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable(Destination.Home.route) { HomeScreen() }
            composable(Destination.Catalogo.route) { CatalogoScreen() }
        }
    }
}