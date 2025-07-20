package karpiuk.bookmary.core_ui.components.buttons.icon_button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        Icon(
            imageVector = model.icon,
            contentDescription = model.contentDescription,
            modifier = Modifier.size(model.iconSize),
            tint = Color.Black
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
                icon = Icons.Default.PlayArrow,
                contentDescription = "Play",
            ),
        )
    }
}
