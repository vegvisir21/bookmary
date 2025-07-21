package karpiuk.bookmary.domain.models

data class BookSummaryModel(
    val id: Int,
    val coverUrl: String,
    val audioSummaryUrl: String,
    val chapters: List<ChapterModel>,
) {

    private val previousChapterDelayTime = 3_000

    fun getNextChapter(millis: Long): ChapterModel? {
        val sorted = chapters.sortedBy { it.time }
        return sorted.firstOrNull { it.time >= millis }
    }

    fun getPreviousChapter(millis: Long): ChapterModel? {
        val sorted = chapters.sortedBy { it.time }

        val current = sorted.lastOrNull { it.time <= millis }
        val currentIndex = sorted.indexOf(current)
        val offset = millis - (current?.time ?: 0L)
        return if (offset < previousChapterDelayTime && currentIndex > 0) {
            sorted[currentIndex - 1]
        } else {
            current
        }
    }
}