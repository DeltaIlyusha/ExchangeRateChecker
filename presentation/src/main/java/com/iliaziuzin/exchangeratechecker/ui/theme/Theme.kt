package com.iliaziuzin.exchangeratechecker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ExchangeRateCheckerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

val ColorScheme.bgCard: Color@Composable
get() = BgCard

val ColorScheme.FavEnabled: Color@Composable
get() = MainYellow

val ColorScheme.FavDisabled: Color@Composable
get() = MainSecondary

val ColorScheme.Primary: Color@Composable
get() = MainPrimary

val ColorScheme.TextDefault: Color@Composable
get() = MainTextDefault

val ColorScheme.TextSecondary: Color@Composable
get() = MainTextSecondary

val ColorScheme.Secondary: Color@Composable
get() = MainSecondary

val ColorScheme.BackgroundDefault: Color@Composable
get() = BgDefault


val ColorScheme.BackgroundHeader: Color@Composable
get() = BgHeader

val ColorScheme.OnPrimary: Color@Composable
get() = MainOnPrimary

val ColorScheme.Outline: Color@Composable
get() = MainOutline

val ColorScheme.LightPrimary: Color@Composable
get() = MainLightPrimary


