package karpiuk.bookmary.core_ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = prussianBlue,
    onPrimary = white,
    secondary = pictonBlue,
    tertiary = webOrange,
    background = white,
    onSurface = prussianBlue,
    onBackground = prussianBlue,
)

private val LightColorScheme = lightColorScheme(
    primary = prussianBlue,
    onPrimary = white,
    secondary = pictonBlue,
    tertiary = webOrange,
    background = white,
    onSurface = prussianBlue,
    onBackground = prussianBlue,
)

@Composable
fun BookmaryTheme(
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

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(
        LocalCustomColorsPalette provides extendedColors,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}