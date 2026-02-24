package com.impend.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = ImpendColors.Primary,
    primaryVariant = ImpendColors.Primary,
    secondary = ImpendColors.Secondary,
    background = ImpendColors.Background,
    surface = ImpendColors.Surface,
    onPrimary = ImpendColors.TextPrimary,
    onSecondary = ImpendColors.Background,
    onBackground = ImpendColors.TextPrimary,
    onSurface = ImpendColors.TextPrimary,
    error = ImpendColors.Error
)

@Composable
fun ImpendTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = ImpendTypography,
        content = content
    )
}
