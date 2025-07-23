package karpiuk.bookmary.presentation.components.media_switcher

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.components.icon.AppIcon
import karpiuk.bookmary.core_ui.components.icon.AppIconType
import karpiuk.bookmary.core_ui.theme.BookmaryTheme
import karpiuk.bookmary.core_ui.theme.customColors

enum class Mode { Audio, Text }

@Composable
fun MediaSwitcher(
    modifier: Modifier = Modifier,
    selected: Mode = Mode.Audio,
    onModeSelected: (Mode) -> Unit = {}
) {
    val background = Color.White
    val activeColor = MaterialTheme.customColors.switcherActiveColor

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .border(
                width = 1.dp,
                color = MaterialTheme.customColors.borderColor,
                shape = RoundedCornerShape(50)
            )
            .padding(4.dp)
    ) {
        ModeSwitcherButton(
            icon = AppIconType.Headphones,
            isSelected = selected == Mode.Audio,
            onClick = { onModeSelected(Mode.Audio) },
            activeColor = activeColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        ModeSwitcherButton(
            icon = AppIconType.Menu,
            isSelected = selected == Mode.Text,
            onClick = { onModeSelected(Mode.Text) },
            activeColor = activeColor
        )
    }
}

@Composable
private fun ModeSwitcherButton(
    icon: AppIconType,
    isSelected: Boolean,
    onClick: () -> Unit,
    activeColor: Color
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(if (isSelected) activeColor else Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AppIcon(
            type = icon,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(if (isSelected) Color.White else Color.Black)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MediaSwitcherPreview() {
    BookmaryTheme {
        MediaSwitcher()
    }
}