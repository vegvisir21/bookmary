package karpiuk.bookmary.presentation.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.domain.models.ChapterModel
import karpiuk.bookmary.presentation.models.BookSummaryUiModel
import karpiuk.bookmary.presentation.screens.AudioPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class SummaryViewModel @Inject constructor(
    private val player: AudioPlayer,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SummaryUiState(
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
    val uiState = _uiState.asStateFlow()

    val audioProgress: StateFlow<Long> = player.progressFlow
    val durationFlow: StateFlow<Long> = player.durationFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                SummaryUiState(
                    bookSummary = BookSummaryUiModel(
                        id = duneTestData.id,
                        coverUrl = duneTestData.coverUrl,
                        audioSummaryUrl = duneTestData.audioSummaryUrl,
                        activeChapter = duneTestData.chapters.first(),
                    ),
                    activeChapterNumber = 1,
                    chaptersTotal = duneTestData.chapters.size,
                )
            }
            withContext(Dispatchers.Main) {
                val duration = player.init(_uiState.value.bookSummary.audioSummaryUrl)
                _uiState.update { it.copy(duration = duration) }
            }
        }
    }

    fun onPlayerClicked() {
        if (player.isPlaying()) stopPlayer() else startPlayer()
    }

    fun onSpeedClicked() {
        val nextSpeed = _uiState.value.playbackSpeed.getNextSpeed()
        player.setPlaybackSpeed(nextSpeed.multiplier)
        _uiState.update { it.copy(playbackSpeed = nextSpeed) }
    }

    fun startPlayer() = player.resume()

    fun stopPlayer() = player.pause()

    private val duneTestData = BookSummaryModel(
        id = 1,
        coverUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/dune_cover.jpg?alt=media&token=67ebb254-26e7-491a-b0eb-82a1b9969d51",
        audioSummaryUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/Dune.mp3?alt=media&token=0463f5e1-ad2b-4f79-9cf6-83e61ee8e720",
        chapters = listOf(
            ChapterModel(
                id = 1,
                title = "Intro",
                time = 0,
            ),
            ChapterModel(
                id = 2,
                title = "Quick Book Summary",
                time = 7000,
            ),
            ChapterModel(
                id = 3,
                title = "Plot Deep Dive",
                time = 45000,
            ),
            ChapterModel(
                id = 4,
                title = "Main Characters",
                time = 366000,
            ),
            ChapterModel(
                id = 5,
                title = "Main themes of the Book",
                time = 578000,
            ),
            ChapterModel(
                id = 6,
                title = "About the Author",
                time = 748000,
            ),
            ChapterModel(
                id = 7,
                title = "Outro",
                time = 795000,
            ),
        ),
    )

}