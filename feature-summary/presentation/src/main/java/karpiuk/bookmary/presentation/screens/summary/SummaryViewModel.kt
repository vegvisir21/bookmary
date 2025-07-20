package karpiuk.bookmary.presentation.screens.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import karpiuk.bookmary.domain.models.ChapterModel
import karpiuk.bookmary.presentation.models.BookSummaryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SummaryViewModel @Inject constructor(
    //TODO future use-cases
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SummaryUiState(
            bookSummary = BookSummaryUiModel(
                id = 1,
                coverUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/dune_cover.jpg?alt=media&token=67ebb254-26e7-491a-b0eb-82a1b9969d51",
                audioSummaryUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/Dune.mp3?alt=media&token=0463f5e1-ad2b-4f79-9cf6-83e61ee8e720",
                activeChapter = ChapterModel(
                    id = 1,
                    title = "Design is not how a thing looks, but how is works",
                    time = 1,
                ),
            ),
            activeChapterNumber = 1,
            chaptersTotal = 10,
        ),
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                SummaryUiState(
                    bookSummary = BookSummaryUiModel(
                        id = 1,
                        coverUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/dune_cover.jpg?alt=media&token=67ebb254-26e7-491a-b0eb-82a1b9969d51",
                        audioSummaryUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/Dune.mp3?alt=media&token=0463f5e1-ad2b-4f79-9cf6-83e61ee8e720",
                        activeChapter = ChapterModel(
                            id = 1,
                            title = "Design is not how a thing looks, but how is works",
                            time = 1,
                        ),
                    ),
                    activeChapterNumber = 1,
                    chaptersTotal = 10,
                )
            }
        }
    }

}