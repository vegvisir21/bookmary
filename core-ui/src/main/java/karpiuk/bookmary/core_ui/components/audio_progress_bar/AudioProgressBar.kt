package karpiuk.bookmary.core_ui.components.audio_progress_bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.components.horizontal_slider.HorizontalSlider
import karpiuk.bookmary.core_ui.components.horizontal_slider.HorizontalSliderModel
import karpiuk.bookmary.core_ui.theme.BookmaryTheme

@Composable
fun AudioProgressBar(
    modifier: Modifier = Modifier,
    model: AudioProgressBarModel
) {
    val sliderValue = remember { mutableFloatStateOf(model.currentTime.toFloat()) }
    val isDragging = remember { mutableStateOf(false) }

    LaunchedEffect(model.currentTime) {
        if (!isDragging.value) {
            sliderValue.value = model.currentTime.toFloat()
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = formatTime(model.currentTime),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
        HorizontalSlider(
            modifier = Modifier.weight(1f),
            model = HorizontalSliderModel(
                value = sliderValue.value,
                valueRange = 0f..model.totalTime.toFloat(),
            ),
            onValueChange = {
                isDragging.value = true
                sliderValue.value = it
            },
            onValueChangeFinished = {
                isDragging.value = false
                model.onSeekChanged(sliderValue.value.toLong())
            }
        )
        Text(
            text = formatTime(model.totalTime),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

fun formatTime(millis: Long): String {
    val totalSec = millis / 1000
    val minutes = totalSec / 60
    val seconds = totalSec % 60
    return "%02d:%02d".format(minutes, seconds)
}

@Preview(showBackground = true)
@Composable
private fun AudioProgressBarPreview() {
    BookmaryTheme {
        AudioProgressBar(
            modifier = Modifier.padding(16.dp),
            model = AudioProgressBarModel(
                currentTime = 118000,
                totalTime = 544000,
            ),
        )
    }
}
