package com.babatman.promisemyself.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight.copy(alpha = 0.1f),
    onPrimaryContainer = TextPrimaryLight,
    
    secondary = SecondaryLight,
    onSecondary = Color.White,
    secondaryContainer = SecondaryLight.copy(alpha = 0.1f),
    onSecondaryContainer = TextSecondaryLight,
    
    tertiary = AccentLight,
    onTertiary = Color.White,
    tertiaryContainer = AccentLight.copy(alpha = 0.1f),
    onTertiaryContainer = AccentLight,
    
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = TextSecondaryLight,
    
    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF991B1B)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color.White,
    primaryContainer = PrimaryDark.copy(alpha = 0.2f),
    onPrimaryContainer = TextPrimaryDark,
    
    secondary = SecondaryDark,
    onSecondary = Color.White,
    secondaryContainer = SecondaryDark.copy(alpha = 0.2f),
    onSecondaryContainer = TextSecondaryDark,
    
    tertiary = AccentDark,
    onTertiary = Color.White,
    tertiaryContainer = AccentDark.copy(alpha = 0.2f),
    onTertiaryContainer = AccentDark,
    
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = TextSecondaryDark,
    
    error = Color(0xFFEF4444),
    onError = Color.White,
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = Color(0xFFFCA5A5)
)

@Composable
fun PromiseMyselfTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}