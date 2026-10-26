package com.mastermartini.navigation

import android.provider.ContactsContract
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mastermartini.Screens.EditProfileScreen
import com.mastermartini.Screens.LoginScreen
import com.mastermartini.Screens.ProfileScreen
import com.mastermartini.Screens.RegisterScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    NavHost(navController = navController, startDestination = AppScreens.loginScreen.route)
    {
        composable(AppScreens.loginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.registerScreen.route) {
            RegisterScreen(navController = navController,
                onRegister = { registeredName, registeredLastName, registeredUsername ->
                    name = registeredName
                    lastName = registeredLastName
                    username = registeredUsername

                    navController.navigate(AppScreens.profileScreen.route) {
                        popUpTo(AppScreens.registerScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppScreens.profileScreen.route) {
            ProfileScreen(navController = navController,
                name = name,
                lastName = lastName,
                username = username
            )
        }
        composable(AppScreens.editProfileScreen.route) {
            EditProfileScreen(
                name = name,
                lastName = lastName,
                username = username,
                onSave = { newName, newLastName, newUsername ->
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
