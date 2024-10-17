package com.github.csc3380fall2024.team16.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    dark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (dark) darkScheme else lightScheme,
        shapes = MaterialTheme.shapes,
        typography = CreateTypography(),
        content = content,
    )
}
