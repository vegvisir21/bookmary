package karpiuk.bookmary.presentation.screens.summary

import karpiuk.bookmary.core_domain.enums.PlaybackSpeed
import karpiuk.bookmary.core_ui.components.player_controller.PlayerControllerModel
import karpiuk.bookmary.presentation.models.BookSummaryUiModel

data class SummaryUiState(
    val bookSummary: BookSummaryUiModel,
    val playbackSpeed: PlaybackSpeed = PlaybackSpeed.Normal,
    val activeChapterNumber: Int,
    val chaptersTotal: Int,
    val duration: Long = 0,
    val playerControllerModel: PlayerControllerModel = PlayerControllerModel(),
)