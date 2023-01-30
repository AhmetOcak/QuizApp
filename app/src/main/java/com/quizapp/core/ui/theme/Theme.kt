package com.quizapp.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = BlackSmoke,
    primaryVariant = TextWhite, // Text - Icon color
    secondary = Teal200,
    background = Black,
    surface = BlackSmoke, // Card background color
    onPrimary = WhiteSmoke
)

@Composable
fun QuizAppTheme(content: @Composable () -> Unit) {
    val colors = DarkColorPalette
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(color = Black, darkIcons = false)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}