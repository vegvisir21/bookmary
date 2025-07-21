@file:OptIn(ExperimentalMaterial3Api::class)

package karpiuk.bookmary.core_ui.components.horizontal_slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.theme.BookmaryTheme
import karpiuk.bookmary.core_ui.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalSlider(
    model: HorizontalSliderModel,
    modifier: Modifier = Modifier,
    onValueChangeFinished: () -> Unit = {},
    onValueChange: (Float) -> Unit = {},
    thumb: @Composable () -> Unit = {
        HorizontalSliderDefaults.Thumb()
    },
) {
    val colors = HorizontalSliderDefaults.colors()

    Slider(
        modifier = modifier,
        value = model.value,
        valueRange = model.valueRange,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished,
        colors = colors,
        thumb = { thumb() },
        track = { sliderState ->
            SliderDefaults.Track(
                modifier = Modifier.height(8.dp),
                colors = colors,
                sliderState = sliderState,
                thumbTrackGapSize = 0.dp,
                drawTick = { _, _ -> },
                drawStopIndicator = { },
            )
        },
        steps = 0,
        enabled = model.enabled
    )
}

object HorizontalSliderDefaults {
    @Composable
    fun colors() = SliderDefaults.colors(
        thumbColor = MaterialTheme.customColors.sliderThumbColor,
        activeTrackColor = MaterialTheme.customColors.sliderThumbColor,
        inactiveTrackColor = MaterialTheme.customColors.sliderTrackColor,
    )

    @Composable
    fun Thumb(
        modifier: Modifier = Modifier,
        color: Color = colors().thumbColor,
        size: Dp = 20.dp,
        shape: Shape = CircleShape,
    ) {
        Box(
            modifier = modifier.size(size),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(shape)
                    .background(color),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HorizontalSliderPreview() {
    BookmaryTheme {
        HorizontalSlider(
            model = HorizontalSliderModel(
                value = 0.4f,
                valueRange = 0f..1f,
                enabled = true,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}