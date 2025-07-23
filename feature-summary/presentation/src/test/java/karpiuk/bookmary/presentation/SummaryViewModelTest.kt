package karpiuk.bookmary.presentation

import io.mockk.*
import junit.framework.TestCase.assertEquals
import karpiuk.bookmary.core_domain.core.Result
import karpiuk.bookmary.core_domain.extensions.result
import karpiuk.bookmary.core_domain.tools.AudioPlayer
import karpiuk.bookmary.core_ui.tools.StringProvider
import karpiuk.bookmary.domain.models.BookSummaryModel
import karpiuk.bookmary.domain.models.ChapterModel
import karpiuk.bookmary.domain.use_cases.GetBookSummaryUseCase
import karpiuk.bookmary.presentation.components.media_switcher.Mode
import karpiuk.bookmary.presentation.screens.summary.SummaryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SummaryViewModelTest {

    private lateinit var viewModel: SummaryViewModel
    private val audioPlayer: AudioPlayer = mockk(relaxed = true)
    private val stringProvider: StringProvider = mockk()
    private val getBookSummaryUseCase: GetBookSummaryUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { stringProvider.getString(any(), *anyVararg()) } returns "Mocked String"

        coEvery { getBookSummaryUseCase.result() } returns flow {
            emit(Result.Loading)
            emit(Result.Success(dummyBookSummary))
        }

        every { audioPlayer.playbackProgress } returns MutableStateFlow(0L)
        every { audioPlayer.duration } returns MutableStateFlow(100000L)
        every { audioPlayer.isPlaying } returns MutableStateFlow(false)

        viewModel = SummaryViewModel(audioPlayer, stringProvider, getBookSummaryUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `seekTo calls player seekTo with correct value`() {
        every { audioPlayer.seekTo(any()) } just Runs

        viewModel.seekTo(123456L)

        verify { audioPlayer.seekTo(123456L) }
    }

    @Test
    fun `onModeChanged updates mediaMode in uiState`() {
        viewModel.onModeChanged(Mode.Text)

        assertEquals(Mode.Text, viewModel.uiState.value.mediaMode)
    }

    @Test
    fun `playNextChapter seeks to next chapter time`() {
        val chapters = listOf(
            ChapterModel(1, "One", 0L),
            ChapterModel(2, "Two", 10_000L),
        )

        val summary = BookSummaryModel(
            id = 1,
            title = "Test",
            coverUrl = "",
            audioSummaryUrl = "",
            chapters = chapters,
        )

        val bookSummaryField = viewModel.javaClass.getDeclaredField("bookSummary")
        bookSummaryField.isAccessible = true
        bookSummaryField.set(viewModel, summary)

        viewModel.onModeChanged(Mode.Audio)
        viewModel.seekTo(0L)

        every { audioPlayer.seekTo(any()) } just Runs

        viewModel.uiState.value.playerControllerModel.onPlayNextClick()

        verify { audioPlayer.seekTo(10_000L) }
    }

    companion object {
        private val dummyChapters = listOf(
            ChapterModel(1, "Intro", 0L),
            ChapterModel(2, "Main", 30000L),
        )

        private val dummyBookSummary = BookSummaryModel(
            id = 1,
            title = "Test Book",
            coverUrl = "url",
            audioSummaryUrl = "audio_url",
            chapters = dummyChapters,
        )
    }
}
