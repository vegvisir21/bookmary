package karpiuk.bookmary.core_ui.components.buttons.icon_button

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.components.icon.AppIconType

data class PrimaryIconButtonModel(
    val icon: AppIconType,
    val iconSize: Dp = 24.dp,
    val iconPadding: Dp = 24.dp,
    val onClick: () -> Unit = {},
) {
    companion object {
        fun large(
            icon: AppIconType,
            onClick: () -> Unit = {},
        ): PrimaryIconButtonModel = PrimaryIconButtonModel(
            icon = icon,
            iconSize = 36.dp,
            iconPadding = 12.dp,
            onClick = onClick,
        )
    }
}