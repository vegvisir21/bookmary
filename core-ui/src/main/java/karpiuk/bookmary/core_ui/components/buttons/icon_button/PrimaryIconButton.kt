package karpiuk.bookmary.core_ui.components.buttons.icon_button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.components.icon.AppIcon
import karpiuk.bookmary.core_ui.components.icon.AppIconType
import karpiuk.bookmary.core_ui.theme.BookmaryTheme

@Composable
fun PrimaryIconButton(
    model: PrimaryIconButtonModel,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(model.iconSize + model.iconPadding)
            .clip(CircleShape)
            .clickable(onClick = model.onClick),
        contentAlignment = Alignment.Center
    ) {
        AppIcon(
            type = model.icon,
            modifier = Modifier.size(model.iconSize),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryIconButtonPreview() {
    BookmaryTheme {
        PrimaryIconButton(
            modifier = Modifier.padding(4.dp),
            model = PrimaryIconButtonModel(
                icon = AppIconType.Rewind5,
            ),
        )
    }
}
