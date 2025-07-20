package karpiuk.bookmary.core_ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

//Primary
internal val prussianBlue = Color(0xFF003851)
internal val webOrange = Color(0xFFF4A800)
internal val pictonBlue = Color(0xFF4FBADE)
internal val tamarillo = Color(0xFF981313)

//Secondary
private val highContrast = Color(0xFFEA6E15)
internal val tealBlue = Color(0xFF03465E)
internal val navyBlue = Color(0xFF455A64)
internal val highlightBlue = Color(0xFF3176A7)
internal val lightGrey = Color(0xFF86868A)
internal val nobel = Color(0xFFB4B4B4)
internal val geyser = Color(0xFFCDDADF)
private val mintTulip = Color(0xFFCAEAF5)
internal val oysterPink = Color(0xFFEAD0D0)
private val bananaMania = Color(0xFFFCE5B3)

//Background
internal val white = Color(0xFFFFFFFF)
internal val alabaster = Color(0xFFF9F9F9)
internal val mercury = Color(0xFFE8E8E8)

@Immutable
data class ExtendedColors(
    val principleConnectionColor: Color = Color.Unspecified,
    val principleConnectionOnBackgroundColor: Color = Color.Unspecified,
)

val LightExtendedColors = ExtendedColors(
    principleConnectionColor = prussianBlue,
    principleConnectionOnBackgroundColor = white,
)

val DarkExtendedColors = ExtendedColors(
    principleConnectionColor = prussianBlue,
    principleConnectionOnBackgroundColor = white,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { ExtendedColors() }

@Suppress("UnusedReceiverParameter")
val MaterialTheme.customColors: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current
