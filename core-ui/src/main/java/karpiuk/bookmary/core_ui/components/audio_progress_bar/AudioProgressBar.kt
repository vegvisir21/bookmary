package karpiuk.bookmary.core_ui.components.audio_progress_bar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import karpiuk.bookmary.core_ui.theme.BookmaryTheme

@Composable
fun AudioProgressBar(
    modifier: Modifier = Modifier,
    model: AudioProgressBarModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = model.currentTime,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(end = 8.dp)
        )

        //TODO remade thumb + style + colors
        Slider(
            value = model.progress.coerceIn(0f, 1f),
            onValueChange = model.onSeekChanged,
            modifier = Modifier
                .weight(1f)
                .height(32.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF007AFF),
                activeTrackColor = Color(0xFF007AFF),
                inactiveTrackColor = Color(0xFFE0E0E0)
            )
        )

        Text(
            text = model.totalTime,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AudioProgressBarPreview() {
    BookmaryTheme {
        AudioProgressBar(
            modifier = Modifier.padding(16.dp),
            model = AudioProgressBarModel(
                progress = 0.37f,
                currentTime = "12:27",
                totalTime = "29:54",
            ),
        )
    }
}
