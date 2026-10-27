package com.mastermartini.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*private val AppColorScheme = darkColorScheme(
    primary = Cyan,
    onPrimary = Color.White,
    secondary = Gold,
    onSecondary = Color.Black,
    tertiary = Khaki,
    onTertiary = Color.White,
    background = Color.White,
    onBackground = SoftBlack
)*/

private val AppColorScheme = lightColorScheme(
    primary = Cyan,
    onPrimary = Color.White,
    secondary = Gold,
    onSecondary = Color.Black,
    tertiary = Khaki,
    onTertiary = Color.White,
    background = Background,
    onBackground = SoftBlack,
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun MasterMartiniTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}