package karpiuk.bookmary.presentation.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import karpiuk.bookmary.core_domain.core.Result
import karpiuk.bookmary.core_domain.extensions.result
import karpiuk.bookmary.core_ui.components.player_controller.PlayerControllerModel
import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.domain.use_cases.GetBookSummaryUseCase
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
    private val getBookSummaryUseCase: GetBookSummaryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState = _uiState.asStateFlow()

    val currentPositionFlow: StateFlow<Long> = player.currentPositionFlow
    val durationFlow: StateFlow<Long> = player.durationFlow

    private lateinit var bookSummary: BookSummaryModel

    init {
        initDefaultState()
        initData()
    }

    private fun initDefaultState() {
        _uiState.update {
            SummaryUiState(
                playerControllerModel = PlayerControllerModel(
                    isPlaying = false,
                    onPlayPreviousClick = ::playPreviousChapter,
                    onRewind = ::rewindPlayer,
                    onPlayClick = ::resumePlayer,
                    onPauseClick = ::pausePlayer,
                    onForward = ::fastForwardPlayer,
                    onPlayNextClick = ::playNextChapter,
                ),
            )
        }
    }

    private fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            getBookSummaryUseCase.result(Unit).collect { result ->
                when (result) {
                    is Result.Error -> {}
                    Result.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Result.Success<BookSummaryModel> -> {
                        bookSummary = result.data
                        withContext(Dispatchers.Main) {
                            val duration = player.init(_uiState.value.bookSummary.audioSummaryUrl)
                            _uiState.update { it.copy(duration = duration) }
                        }
                        player.currentPositionFlow.collect { position ->
                            updateActiveChapter(position)
                        }
                    }
                }
            }
        }
    }

    private fun updateActiveChapter(millis: Long) {
        val activeChapter = _uiState.value.bookSummary.activeChapter
        val chapter = bookSummary.chapters
            .lastOrNull { it.time <= millis }

        chapter?.let { newChapter ->
            if (activeChapter.id != chapter.id) {
                _uiState.update {
                    it.copy(
                        bookSummary = it.bookSummary.copy(
                            activeChapter = newChapter
                        ),
                        activeChapterNumber = bookSummary.chapters.indexOf(newChapter) + 1
                    )
                }
            }
        }
    }

    fun onSpeedClicked() {
        val nextSpeed = _uiState.value.playbackSpeed.getNextSpeed()
        player.setPlaybackSpeed(nextSpeed.multiplier)
        _uiState.update { it.copy(playbackSpeed = nextSpeed) }
    }

    fun seekTo(millis: Long) {
        player.seekTo(millis)
    }

    private fun refreshPlayer() {
        //TODO move to the player logic
        player.pause()
        player.seekTo(0)
        _uiState.update {
            val playerControllerModel = it.playerControllerModel
            it.copy(
                playerControllerModel = playerControllerModel.copy(
                    isPlaying = false,
                )
            )
        }
    }

    private fun resumePlayer() {
        player.resume()
        _uiState.update {
            val playerControllerModel = it.playerControllerModel
            it.copy(
                playerControllerModel = playerControllerModel.copy(
                    isPlaying = true,
                )
            )
        }
    }

    private fun pausePlayer() {
        player.pause()
        _uiState.update {
            val playerControllerModel = it.playerControllerModel
            it.copy(
                playerControllerModel = playerControllerModel.copy(
                    isPlaying = false,
                )
            )
        }
    }

    private fun rewindPlayer() {
        player.rewind(PlayerControllerModel.REWIND_MILLIS)
    }

    private fun fastForwardPlayer() {
        player.fastForward(PlayerControllerModel.FAST_FORWARD_MILLIS)
    }

    private fun playNextChapter() {
        val nextChapter = bookSummary.getNextChapter(_uiState.value.bookSummary.activeChapter)
        if (nextChapter == null) {
            refreshPlayer()
        }
        val newPosition = nextChapter?.time ?: 0
        player.seekTo(newPosition)
    }

    private fun playPreviousChapter() {
        val currentChapter = _uiState.value.bookSummary.activeChapter
        val delayTime = BookSummaryModel.PREVIOUS_CHAPTER_DELAY
        val newPosition = if (currentPositionFlow.value - currentChapter.time >= delayTime) {
            currentChapter.time
        } else {
            bookSummary.getPreviousChapter(currentChapter).time
        }
        player.seekTo(newPosition)
    }

}