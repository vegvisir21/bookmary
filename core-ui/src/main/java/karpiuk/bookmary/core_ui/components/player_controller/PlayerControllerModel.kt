package karpiuk.bookmary.core_ui.components.player_controller

data class PlayerControllerModel(
    val isPlaying: Boolean = false,
    val onPlayClick: () -> Unit = {},
    val onPauseClick: () -> Unit = {},
    val onPlayPreviousClick: () -> Unit = {},
    val onPlayNextClick: () -> Unit = {},
    val onRewind: () -> Unit = {},
    val onForward: () -> Unit = {},
) {
    companion object {
        const val REWIND_MILLIS = 5000L
        const val FAST_FORWARD_MILLIS = 10000L
    }
}