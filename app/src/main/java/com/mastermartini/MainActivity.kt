package com.mastermartini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mastermartini.navigation.AppNavigation
import com.mastermartini.ui.theme.MasterMartiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MasterMartiniTheme {
                AppNavigation()
            }
        }
    }
}
