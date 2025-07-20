package karpiuk.bookmary.presentation.models

import karpiuk.bookmary.domain.models.ChapterModel

data class BookSummaryUiModel(
    val id: Int,
    val coverUrl: String,
    val audioSummaryUrl: String,
    val activeChapter: ChapterModel,
    val duration: Int = 0,
)