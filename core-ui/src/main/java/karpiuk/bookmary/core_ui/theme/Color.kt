package karpiuk.bookmary.core_ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val floralWhite = Color(0xFFFFF9F4)
internal val alabaster = Color(0xFFF1EBE8)
internal val brandeisBlue = Color(0xFF0066FC)
internal val blueJeans = Color(0xFF55B6Fc)
internal val whiteCoffee = Color(0xFFE6DFDB)

internal val raisinBlack = Color(0xFF272421)
internal val spanishGray = Color(0xFF9F9994)

@Immutable
data class ExtendedColors(
    val textPrimaryColor: Color = Color.Unspecified,
    val textSecondaryColor: Color = Color.Unspecified,
    val optionButtonBackgroundColor: Color = Color.Unspecified,
    val sliderThumbColor: Color = Color.Unspecified,
    val sliderTrackColor: Color = Color.Unspecified,
    val switcherActiveColor: Color = Color.Unspecified,
    val borderColor: Color = Color.Unspecified,
    val loadingProgressColor: Color = Color.Unspecified,
)

val LightExtendedColors = ExtendedColors(
    textPrimaryColor = raisinBlack,
    textSecondaryColor = spanishGray,
    optionButtonBackgroundColor = alabaster,
    sliderThumbColor = brandeisBlue,
    sliderTrackColor = whiteCoffee,
    switcherActiveColor = brandeisBlue,
    borderColor = spanishGray,
    loadingProgressColor = blueJeans,
)

val DarkExtendedColors = ExtendedColors(
    textPrimaryColor = raisinBlack,
    textSecondaryColor = spanishGray,
    optionButtonBackgroundColor = alabaster,
    sliderThumbColor = brandeisBlue,
    sliderTrackColor = whiteCoffee,
    switcherActiveColor = brandeisBlue,
    borderColor = spanishGray,
    loadingProgressColor = blueJeans,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { ExtendedColors() }

@Suppress("UnusedReceiverParameter")
val MaterialTheme.customColors: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current
