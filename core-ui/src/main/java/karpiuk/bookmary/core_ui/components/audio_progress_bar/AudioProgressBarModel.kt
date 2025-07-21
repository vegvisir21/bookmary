package karpiuk.bookmary.core_ui.components.audio_progress_bar

data class AudioProgressBarModel(
    val currentTime: Long = 0L,
    val totalTime: Long = 0L,
    val onSeekChanged: (Long) -> Unit = {},
)
