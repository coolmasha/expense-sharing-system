package com.mashakulabukhova.expensesharingsystem.presentation.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
)

private val LightColorScheme = lightColorScheme(

    primary = Blue400,
    onPrimary = White,
    primaryContainer = White,
    onPrimaryContainer = Black,

    secondary = Blue300,
    onSecondary = White,
    secondaryContainer = Blue200,

    tertiary = Grey100,
    onTertiary = White,

    background = White,
    onBackground = Black,

    surface = White,
    onSurface = Grey100,
    onSurfaceVariant = Grey500,

    surfaceTint = Blue100,

    outline = White,



    error = Red200,
    onError = Red800,
    errorContainer = Red200,
    onErrorContainer = Red800
)

@Composable
fun ExpenseSharingSystemTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}