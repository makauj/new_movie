package com.moviestream.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color Palette
val primary = Color(0xFF1F1F2E)
val secondary = Color(0xFFE50914)
val tertiary = Color(0xFF221E22)
val background = Color(0xFF141414)
val surface = Color(0xFF1F1F1F)
val error = Color(0xFFFF5252)
val onPrimary = Color(0xFFFFFFFF)
val onSecondary = Color(0xFFFFFFFF)
val onTertiary = Color(0xFFFFFFFF)
val onBackground = Color(0xFFFFFFFF)
val onSurface = Color(0xFFFFFFFF)

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onTertiary = onTertiary,
    onBackground = onBackground,
    onSurface = onSurface
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    background = Color.White,
    surface = Color(0xFFFAFAFA),
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onTertiary = onTertiary,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun MovieStreamTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
