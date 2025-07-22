package karpiuk.bookmary.data.repositories

import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.domain.models.ChapterModel
import karpiuk.bookmary.domain.repositories.BookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    override suspend fun getBookSummary(): Flow<BookSummaryModel> {
        //TODO api implementation via DataSource and mapping from Dto to Domain model
        return flow {
            delay(1500)
            emit(
                BookSummaryModel(
                    id = 1,
                    coverUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/dune_cover.jpg?alt=media&token=67ebb254-26e7-491a-b0eb-82a1b9969d51",
                    audioSummaryUrl = "https://firebasestorage.googleapis.com/v0/b/bookmary-5087f.firebasestorage.app/o/Dune.mp3?alt=media&token=0463f5e1-ad2b-4f79-9cf6-83e61ee8e720",
                    chapters = listOf(
                        ChapterModel(
                            id = 1,
                            title = "Intro",
                            time = 0,
                        ),
                        ChapterModel(
                            id = 2,
                            title = "Quick Book Summary",
                            time = 7000,
                        ),
                        ChapterModel(
                            id = 3,
                            title = "Plot Deep Dive",
                            time = 45000,
                        ),
                        ChapterModel(
                            id = 4,
                            title = "Main Characters",
                            time = 366000,
                        ),
                        ChapterModel(
                            id = 5,
                            title = "Main themes of the Book",
                            time = 578000,
                        ),
                        ChapterModel(
                            id = 6,
                            title = "About the Author",
                            time = 748000,
                        ),
                        ChapterModel(
                            id = 7,
                            title = "Outro",
                            time = 795000,
                        ),
                    ),
                )
            )
        }
    }

}