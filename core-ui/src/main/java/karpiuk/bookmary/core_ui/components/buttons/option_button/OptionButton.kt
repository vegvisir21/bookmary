package karpiuk.bookmary.core_ui.components.buttons.option_button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.theme.BookmaryTheme
import karpiuk.bookmary.core_ui.theme.customColors

@Composable
fun OptionButton(
    model: OptionButtonModel,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.customColors.optionButtonBackgroundColor,
    contentColor: Color = MaterialTheme.customColors.textPrimaryColor,
    shape: Shape = RoundedCornerShape(8.dp),
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .clickable(onClick = model.onClick)
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = model.title,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Preview
@Composable
private fun OptionButtonPreview() {
    BookmaryTheme {
        OptionButton(
            model = OptionButtonModel(
                title = "Speed x1"
            )
        )
    }
}
