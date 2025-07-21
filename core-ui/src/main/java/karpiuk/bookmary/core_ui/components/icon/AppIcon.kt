package karpiuk.bookmary.core_ui.components.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import karpiuk.bookmary.core_ui.theme.BookmaryTheme

@Composable
fun AppIcon(
    type: AppIconType,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    colorFilter: ColorFilter? = null,
) {
    Image(
        modifier = modifier,
        painter = painterResource(type.resId),
        contentDescription = null,
        contentScale = contentScale,
        colorFilter = colorFilter,
    )
}

@Preview
@Composable
private fun AppIconPreview() {
    BookmaryTheme {
        AppIcon(type = AppIconType.Pause)
    }
}

@Preview
@Composable
private fun AppIconPreview2() {
    BookmaryTheme {
        AppIcon(type = AppIconType.Play)
    }
}

@Preview
@Composable
private fun AppIconPreview3() {
    BookmaryTheme {
        AppIcon(type = AppIconType.Rewind5)
    }
}