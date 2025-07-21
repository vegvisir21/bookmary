package karpiuk.bookmary.presentation.screens

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class AudioPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    private val _currentPositionFlow = MutableStateFlow(0L)
    val currentPositionFlow: StateFlow<Long> = _currentPositionFlow

    private val _durationFlow = MutableStateFlow(0L)
    val durationFlow: StateFlow<Long> = _durationFlow

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    val duration = exoPlayer.duration.takeIf { it > 0 } ?: 0L
                    _durationFlow.value = duration
                }
            }
        })
    }

    fun init(url: String): Long {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = false
        startProgressUpdates()
        return exoPlayer.duration
    }

    private fun startProgressUpdates() {
        scope.launch {
            while (isActive) {
                _currentPositionFlow.emit(exoPlayer.currentPosition)
                delay(500L)
            }
        }
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun resume() {
        exoPlayer.play()
    }

    fun rewind(millis: Long) {
        val newPosition = (exoPlayer.currentPosition - millis).coerceAtLeast(0)
        exoPlayer.seekTo(newPosition)
    }

    fun fastForward(millis: Long) {
        val newPosition = (exoPlayer.currentPosition + millis).coerceAtMost(exoPlayer.duration)
        exoPlayer.seekTo(newPosition)
    }

    fun seekTo(millis: Long) = exoPlayer.seekTo(millis)

    fun stop() = exoPlayer.stop()

    fun release() = exoPlayer.release()

    fun setPlaybackSpeed(speed: Float) = exoPlayer.setPlaybackSpeed(speed)

    fun isPlaying(): Boolean = exoPlayer.isPlaying
}
