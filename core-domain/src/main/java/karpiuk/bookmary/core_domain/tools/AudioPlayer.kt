package karpiuk.bookmary.core_domain.tools

import kotlinx.coroutines.flow.StateFlow

interface AudioPlayer {
    val isPlaying: StateFlow<Boolean>
    val playbackProgress: StateFlow<Long>
    val duration: StateFlow<Long>

    fun prepare(url: String)

    fun play()
    fun pause()
    fun refresh(startAfter: Boolean)

    fun seekTo(positionMillis: Long)
    fun rewind(millis: Long)
    fun fastForward(millis: Long)

    fun setPlaybackSpeed(speed: Float)

    fun release()
}