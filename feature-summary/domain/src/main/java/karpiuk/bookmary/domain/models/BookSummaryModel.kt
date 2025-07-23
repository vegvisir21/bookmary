package karpiuk.bookmary.domain.models

data class BookSummaryModel(
    val id: Int,
    val title: String,
    val coverUrl: String,
    val audioSummaryUrl: String,
    val chapters: List<ChapterModel>,
) {
    companion object {
        const val PREVIOUS_CHAPTER_DELAY = 3_000
    }

    fun getNextChapter(current: ChapterModel): ChapterModel? {
        val sorted = chapters.sortedBy { it.time }
        val index = sorted.indexOfFirst { it.id == current.id }

        return if (index in 0 until sorted.lastIndex) {
            sorted[index + 1]
        } else null
    }

    fun getPreviousChapter(current: ChapterModel): ChapterModel {
        val sorted = chapters.sortedBy { it.time }
        val index = sorted.indexOfFirst { it.id == current.id }

        return if (index > 0) {
            sorted[index - 1]
        } else chapters.first()
    }
}