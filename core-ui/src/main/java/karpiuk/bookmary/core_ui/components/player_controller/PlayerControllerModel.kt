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
        val rewindMillis = 5000L
        val fastForwardMillis = 10000L
    }
}