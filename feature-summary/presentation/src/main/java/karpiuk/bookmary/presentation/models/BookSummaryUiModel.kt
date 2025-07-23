package karpiuk.bookmary.presentation.models

import karpiuk.bookmary.domain.models.ChapterModel

data class BookSummaryUiModel(
    val id: Int = 0,
    val title: String = "",
    val coverUrl: String = "",
    val audioSummaryUrl: String = "",
    val activeChapter: ChapterModel = ChapterModel(1, "", 0),
    val duration: Int = 0,
)