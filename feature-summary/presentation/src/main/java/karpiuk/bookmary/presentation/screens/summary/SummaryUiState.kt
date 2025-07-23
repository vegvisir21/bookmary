package karpiuk.bookmary.presentation.screens.summary

import karpiuk.bookmary.core_ui.components.player_controller.PlayerControllerModel
import karpiuk.bookmary.presentation.components.media_switcher.Mode
import karpiuk.bookmary.presentation.models.BookSummaryUiModel

data class SummaryUiState(
    val bookSummary: BookSummaryUiModel = BookSummaryUiModel(),
    val playerControllerModel: PlayerControllerModel = PlayerControllerModel(),
    val speedTitle: String = "",
    val chaptersCounterTitle: String = "",
    val duration: Long = 0,
    val mediaMode: Mode = Mode.Audio,
    val isLoading: Boolean = false,
)