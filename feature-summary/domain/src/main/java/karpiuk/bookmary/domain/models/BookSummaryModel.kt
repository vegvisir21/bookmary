package karpiuk.bookmary.domain.models

data class BookSummaryModel(
    val id: Int,
    val coverUrl: String,
    val audioSummaryUrl: String,
    val chapters: List<ChapterModel>,
)