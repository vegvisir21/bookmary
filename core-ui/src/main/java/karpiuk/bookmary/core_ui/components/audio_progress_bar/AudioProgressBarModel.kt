package karpiuk.bookmary.core_ui.components.audio_progress_bar

data class AudioProgressBarModel(
    val progress: Float = 0f,
    val currentTime: String = "",
    val totalTime: String = "",
    val onSeekChanged: (Float) -> Unit = {},
)
