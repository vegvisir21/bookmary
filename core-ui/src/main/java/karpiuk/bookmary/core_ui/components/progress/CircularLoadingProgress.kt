package karpiuk.bookmary.core_ui.components.progress

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.theme.BookmaryTheme
import karpiuk.bookmary.core_ui.theme.customColors

@Composable
fun CircularLoadingProgress(
    type: CircularLoadingProgressType,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
) {
    when (type) {
        CircularLoadingProgressType.Default -> {
            CircularLoadingProgress(
                modifier = modifier,
                strokeWidth = strokeWidth,
            )
        }
        CircularLoadingProgressType.ScreenLoader -> {
            CircularLoadingProgress(
                modifier = modifier.size(64.dp),
                strokeWidth = strokeWidth,
            )
        }
    }
}

@Composable
private fun CircularLoadingProgress(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.customColors.loadingProgressColor,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        trackColor = trackColor,
        strokeWidth = strokeWidth,
    )
}

@Preview
@Composable
private fun MsCircularLoadingProgressDefaultPreview() {
    BookmaryTheme {
        CircularLoadingProgress(type = CircularLoadingProgressType.Default)
    }
}

@Preview
@Composable
private fun MsCircularLoadingProgressScreenLoaderPreview() {
    BookmaryTheme {
        CircularLoadingProgress(type = CircularLoadingProgressType.ScreenLoader)
    }
}

enum class CircularLoadingProgressType {
    Default,
    ScreenLoader,
}