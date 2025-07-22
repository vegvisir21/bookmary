package karpiuk.bookmary.domain.repositories

import karpiuk.bookmary.domain.models.BookSummaryModel
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getBookSummary(): Flow<BookSummaryModel>
}