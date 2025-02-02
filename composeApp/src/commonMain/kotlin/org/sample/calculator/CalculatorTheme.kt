package org.sample.calculator

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun themeColors(): Colors {
    return Colors(
        primary = Color.Gray,
        primaryVariant = Color.DarkGray,
        secondary = Color(242, 163, 60, 255),
        secondaryVariant = Color.Yellow,
        background = Color.Black,
        surface = MaterialTheme.colors.surface,
        error = MaterialTheme.colors.error,
        onPrimary = MaterialTheme.colors.onPrimary,
        onSecondary = MaterialTheme.colors.onSecondary,
        onBackground = MaterialTheme.colors.onBackground,
        onSurface = MaterialTheme.colors.onSurface,
        onError = MaterialTheme.colors.onError,
        isLight = MaterialTheme.colors.isLight
    )
}
