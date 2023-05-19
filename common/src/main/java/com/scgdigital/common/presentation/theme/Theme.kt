package com.scgdigital.common.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets

private val LightColorPalette = lightColors(
    primary = Primary600,
    primaryVariant = Primary700,
    secondary = Secondary900,
    secondaryVariant = Secondary800,
    background = Color.White,
    surface = Color.White,
    error = Error,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

@Composable
fun SCGDigitalAndroidComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    ProvideWindowInsets {
        MaterialTheme(
            colors = LightColorPalette,
            shapes = Shapes,
            content = content
        )
    }
}
