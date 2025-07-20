package karpiuk.bookmary.presentation.screens.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import karpiuk.bookmary.core_domain.extensions.toTrimmedString
import karpiuk.bookmary.core_ui.components.audio_progress_bar.AudioProgressBar
import karpiuk.bookmary.core_ui.components.audio_progress_bar.AudioProgressBarModel
import karpiuk.bookmary.core_ui.components.buttons.option_button.OptionButton
import karpiuk.bookmary.core_ui.components.buttons.option_button.OptionButtonModel
import karpiuk.bookmary.core_ui.theme.BookmaryTheme
import karpiuk.bookmary.core_ui.theme.customColors
import karpiuk.bookmary.domain.models.ChapterModel
import karpiuk.bookmary.presentation.models.BookSummaryUiModel

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
) {

    val viewModel: SummaryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val audioProgressMs by viewModel.audioProgress.collectAsState()
    val duration by viewModel.durationFlow.collectAsState()

    val progressFraction = remember(audioProgressMs) {
        if (uiState.duration == 0L) 0f else audioProgressMs / uiState.duration.toFloat()
    }

    SummaryScreen(
        modifier = modifier,
        uiState = uiState,
        playerProgress = progressFraction,
        progress = audioProgressMs,
        duration = duration,
        onPlayClicked = viewModel::onPlayerClicked,
        onSpeedClicked = viewModel::onSpeedClicked,
    )
}

@Composable
private fun SummaryScreen(
    modifier: Modifier = Modifier,
    uiState: SummaryUiState,
    playerProgress: Float = 0f,
    progress: Long = 0L,
    duration: Long = 0L,
    onPlayClicked: () -> Unit = {},
    onSpeedClicked: () -> Unit = {},
) {
    val imageWeight = 4f
    val contentWeight = 6f

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets.statusBars
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
                .padding(vertical = 20.dp),
        ) {

            BookCover(
                modifier = Modifier
                    .weight(imageWeight)
                    .fillMaxSize(),
                coverUrl = uiState.bookSummary.coverUrl,
            )
            Box(
                modifier = Modifier
                    .weight(contentWeight)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    ChapterInfo(
                        chapterNumber = uiState.activeChapterNumber,
                        chaptersTotal = uiState.chaptersTotal,
                        chapterTitle = uiState.bookSummary.activeChapter.title,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    AudioProgressBar(
                        model = AudioProgressBarModel(
                            progress = playerProgress,
                            currentTime = formatTime(progress),
                            totalTime = formatTime(duration),
                            onSeekChanged = {
                                //
                            },
                        ),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OptionButton(
                        model = OptionButtonModel(
                            title = "Speed x${uiState.playbackSpeed.multiplier.toTrimmedString()}",
                            onClick = onSpeedClicked,
                        ),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = onPlayClicked) {
                        Text("Play")
                    }
                }
            }
        }
    }
}

fun formatTime(millis: Long): String {
    val totalSec = millis / 1000
    val minutes = totalSec / 60
    val seconds = totalSec % 60
    return "%02d:%02d".format(minutes, seconds)
}

@Composable
private fun BookCover(
    modifier: Modifier = Modifier,
    coverUrl: String,
) {
    AsyncImage(
        model = coverUrl,
        contentDescription = null,
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(16.dp)),
        contentScale = ContentScale.Inside
    )
}

@Composable
private fun ChapterInfo(
    modifier: Modifier = Modifier,
    chapterNumber: Int,
    chaptersTotal: Int,
    chapterTitle: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Key point $chapterNumber of $chaptersTotal".uppercase(),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.customColors.textSecondaryColor,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = chapterTitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.customColors.textPrimaryColor,
            ),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryScreenPreview() {
    BookmaryTheme {
        SummaryScreen(
            uiState = SummaryUiState(
                bookSummary = BookSummaryUiModel(
                    id = 1,
                    coverUrl = "",
                    audioSummaryUrl = "",
                    activeChapter = ChapterModel(
                        id = 1,
                        title = "Design is not how a thing looks, but how is works",
                        time = 1,
                    ),
                ),
                activeChapterNumber = 1,
                chaptersTotal = 10,
            ),
        )
    }
}