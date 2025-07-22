package karpiuk.bookmary.core_ui.components.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import karpiuk.bookmary.core_ui.components.progress.CircularLoadingProgress
import karpiuk.bookmary.core_ui.components.progress.CircularLoadingProgressType
import karpiuk.bookmary.core_ui.theme.BookmaryTheme

@Composable
fun LoadingContainer(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {

    Box(modifier = modifier.wrapContentSize()) {
        content()
        if (isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = .4f))
                    .zIndex(1f)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {},
                    ),
            ) {
                CircularLoadingProgress(
                    type = CircularLoadingProgressType.ScreenLoader,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingContainerPreview() {
    BookmaryTheme {
        LoadingContainer(true) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) {
                    Text("Text $it")
                }
            }
        }
    }
}