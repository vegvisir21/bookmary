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

    private val _progressFlow = MutableStateFlow(0L)
    val progressFlow: StateFlow<Long> = _progressFlow

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
                _progressFlow.emit(exoPlayer.currentPosition)
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

    fun stop() {
        exoPlayer.stop()
    }

    fun release() {
        exoPlayer.release()
    }

    fun setPlaybackSpeed(speed: Float) = exoPlayer.setPlaybackSpeed(speed)

    fun getDuration(): Long = exoPlayer.contentDuration

    fun isPlaying(): Boolean = exoPlayer.isPlaying
}
