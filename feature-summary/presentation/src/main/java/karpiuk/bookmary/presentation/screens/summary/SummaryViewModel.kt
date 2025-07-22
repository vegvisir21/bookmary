package karpiuk.bookmary.presentation.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import karpiuk.bookmary.core_domain.core.Result
import karpiuk.bookmary.core_domain.extensions.result
import karpiuk.bookmary.core_domain.tools.AudioPlayer
import karpiuk.bookmary.core_ui.components.player_controller.PlayerControllerModel
import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.domain.use_cases.GetBookSummaryUseCase
import karpiuk.bookmary.presentation.mappers.mapToUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    //to avoid too much updates on uiState
    val currentProgressFlow: StateFlow<Long> = player.playbackProgress

    private var bookSummary: BookSummaryModel? = null

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
        observePlayerData()
        viewModelScope.launch(Dispatchers.IO) {
            getBookSummaryUseCase.result(Unit).collect { result ->
                when (result) {
                    is Result.Error -> {}
                    is Result.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Result.Success<BookSummaryModel> -> {
                        val loadedBookSummary = result.data
                        bookSummary = loadedBookSummary
                        _uiState.update {
                            it.copy(
                                bookSummary = loadedBookSummary.mapToUi(),
                                chaptersTotal = loadedBookSummary.chapters.size,
                                isLoading = false,
                            )
                        }
                        withContext(Dispatchers.Main) {
                            player.prepare(_uiState.value.bookSummary.audioSummaryUrl)
                        }
                    }
                }
            }
        }
    }

    private fun observePlayerData() {
        player.isPlaying.onEach { isPlaying ->
            val playerController = _uiState.value.playerControllerModel
            _uiState.update {
                it.copy(
                    playerControllerModel = playerController.copy(
                        isPlaying = isPlaying
                    )
                )
            }
        }.launchIn(viewModelScope)

        player.duration.onEach { duration ->
            _uiState.update { it.copy(duration = duration) }
        }.launchIn(viewModelScope)

        player.playbackProgress.onEach {
            updateActiveChapter(it)
        }.launchIn(viewModelScope)
    }

    private fun updateActiveChapter(millis: Long) = bookSummary?.let { bookSummary ->
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

    fun seekTo(millis: Long) = player.seekTo(millis)

    private fun refreshPlayer() = player.refresh()

    private fun resumePlayer() = player.play()

    private fun pausePlayer() = player.pause()

    private fun rewindPlayer() {
        player.rewind(PlayerControllerModel.REWIND_MILLIS)
    }

    private fun fastForwardPlayer() {
        player.fastForward(PlayerControllerModel.FAST_FORWARD_MILLIS)
    }

    private fun playNextChapter() = bookSummary?.let {
        val nextChapter = it.getNextChapter(_uiState.value.bookSummary.activeChapter)
        if (nextChapter == null) {
            refreshPlayer()
        }
        val newPosition = nextChapter?.time ?: 0
        player.seekTo(newPosition)
    }

    private fun playPreviousChapter() = bookSummary?.let {
        val currentChapter = _uiState.value.bookSummary.activeChapter
        val delayTime = BookSummaryModel.PREVIOUS_CHAPTER_DELAY
        val newPosition = if (currentProgressFlow.value - currentChapter.time >= delayTime) {
            currentChapter.time
        } else {
            it.getPreviousChapter(currentChapter).time
        }
        player.seekTo(newPosition)
    }

}