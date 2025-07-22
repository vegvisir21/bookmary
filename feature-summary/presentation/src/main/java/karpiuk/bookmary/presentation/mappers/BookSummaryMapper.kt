package karpiuk.bookmary.presentation.mappers

import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.presentation.models.BookSummaryUiModel

fun BookSummaryModel.mapToUi() : BookSummaryUiModel {
    return BookSummaryUiModel(
        id = id,
        coverUrl = coverUrl,
        audioSummaryUrl = audioSummaryUrl,
        activeChapter = chapters.first(),
    )
}