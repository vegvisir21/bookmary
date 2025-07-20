package karpiuk.bookmary.presentation.screens.summary

import karpiuk.bookmary.presentation.models.BookSummaryUiModel

data class SummaryUiState(
    val bookSummary: BookSummaryUiModel,
    val activeChapterNumber: Int,
    val chaptersTotal: Int,
    val duration: Long = 0,
)