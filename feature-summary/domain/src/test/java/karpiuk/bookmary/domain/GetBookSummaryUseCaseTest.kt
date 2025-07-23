package karpiuk.bookmary.domain

import io.mockk.coEvery
import io.mockk.mockk
import karpiuk.bookmary.core_domain.core.Result
import karpiuk.bookmary.core_domain.extensions.result
import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.domain.models.ChapterModel
import karpiuk.bookmary.domain.repositories.BookRepository
import karpiuk.bookmary.domain.use_cases.GetBookSummaryUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetBookSummaryUseCaseTest {

    private lateinit var repository: BookRepository
    private lateinit var useCase: GetBookSummaryUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetBookSummaryUseCase(repository)
    }

    @Test
    fun `result returns Success when repository returns data`() = runTest {
        val dummy = BookSummaryModel(
            id = 1,
            title = "Test Book",
            coverUrl = "url",
            audioSummaryUrl = "audio.mp3",
            chapters = listOf(ChapterModel(1, "Intro", 0L))
        )

        coEvery { repository.getBookSummary() } returns flow {
            emit(dummy)
        }

        val result = useCase.result().last()
        assertTrue(result is Result.Success)
        assertEquals("Test Book", (result as Result.Success).data.title)
    }

    @Test
    fun `result returns Error when repository throws`() = runTest {
        coEvery { repository.getBookSummary() } returns flow {
            throw IllegalStateException("Network error")
        }

        val result = useCase.result().last()
        assertTrue(result is Result.Error)
        assertEquals("Network error", (result as Result.Error).throwable.message)
    }
}
