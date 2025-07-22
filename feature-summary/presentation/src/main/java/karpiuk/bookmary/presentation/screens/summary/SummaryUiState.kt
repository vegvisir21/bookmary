package karpiuk.bookmary.presentation.screens.summary

import karpiuk.bookmary.core_domain.enums.PlaybackSpeed
import karpiuk.bookmary.core_ui.components.player_controller.PlayerControllerModel
import karpiuk.bookmary.presentation.models.BookSummaryUiModel

data class SummaryUiState(
    val bookSummary: BookSummaryUiModel = BookSummaryUiModel(),
    val playerControllerModel: PlayerControllerModel = PlayerControllerModel(),
    val playbackSpeed: PlaybackSpeed = PlaybackSpeed.Normal,
    val activeChapterNumber: Int = 0,
    val chaptersTotal: Int = 0,
    val duration: Long = 0,
    val isLoading: Boolean = false,
)