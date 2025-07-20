package karpiuk.bookmary.core_ui.components.buttons.icon_button

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PrimaryIconButtonModel(
    val icon: ImageVector,
    val iconSize: Dp = 24.dp,
    val iconPadding: Dp = 24.dp,
    val contentDescription: String? = null,
    val onClick: () -> Unit = {},
) {
    companion object {
        fun large(
            icon: ImageVector,
            contentDescription: String? = null,
            onClick: () -> Unit = {},
        ): PrimaryIconButtonModel = PrimaryIconButtonModel(
            icon = icon,
            iconSize = 36.dp,
            iconPadding = 12.dp,
            contentDescription = contentDescription,
            onClick = onClick,
        )
    }
}