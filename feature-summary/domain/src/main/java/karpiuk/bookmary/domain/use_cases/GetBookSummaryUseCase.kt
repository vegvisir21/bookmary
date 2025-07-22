package karpiuk.bookmary.domain.use_cases

import karpiuk.bookmary.core_domain.use_cases.FlowUseCase
import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.domain.repositories.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookSummaryUseCase @Inject constructor(
    private val bookRepository: BookRepository
) : FlowUseCase<Unit, BookSummaryModel> {

    override suspend fun execute(params: Unit): Flow<BookSummaryModel> {
        return bookRepository.getBookSummary()
    }

}