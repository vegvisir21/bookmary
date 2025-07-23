package karpiuk.bookmary.core_player

import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import karpiuk.bookmary.core_domain.tools.AudioPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ExoAudioPlayer(
    @ApplicationContext private val context: Context
) : AudioPlayer {

    private var isReleased = false

    private var player: ExoPlayer = ExoPlayer.Builder(context).build()

    private val _isPlaying = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0L)
    override val playbackProgress: StateFlow<Long> = _playbackProgress.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long> = _duration.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var progressJob: Job? = null

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            if (state == Player.STATE_READY) {
                _duration.value = player.duration.coerceAtLeast(0)
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            val state = player.playbackState
            val shouldEmit = when (state) {
                Player.STATE_READY, Player.STATE_ENDED -> true
                else -> isPlayCompleted()
            }

            if (shouldEmit) {
                _isPlaying.value = isPlaying
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            Log.e("PlayerError", error.message.toString())
        }
    }

    override fun prepare(url: String) {
        _isPlaying.value = false
        _duration.value = 0L
        _playbackProgress.value = 0L

        if (isReleased) {
            player = ExoPlayer.Builder(context).build()
            isReleased = false
        }
        player.addListener(playerListener)
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = false
        startProgressUpdates()
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive) {
                val realPosition = if (isPlayCompleted()) {
                    player.pause()
                    player.duration
                } else {
                    player.currentPosition
                }
                _playbackProgress.emit(realPosition.coerceAtLeast(0L))
                delay(250L)
            }
        }
    }

    override fun play() {
        if (isPlayCompleted()) refresh(startAfter = true)
        else player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun refresh(startAfter: Boolean) {
        player.seekTo(0)
        if (startAfter) player.play() else {
            _isPlaying.value = false
            player.pause()
        }
    }

    override fun seekTo(positionMillis: Long) {
        player.seekTo(positionMillis)
    }

    override fun rewind(millis: Long) {
        seekTo((player.currentPosition - millis).coerceAtLeast(0))
    }

    override fun fastForward(millis: Long) {
        seekTo((player.currentPosition + millis).coerceAtMost(player.duration))
    }

    override fun setPlaybackSpeed(speed: Float) {
        player.setPlaybackSpeed(speed)
    }

    override fun release() {
        progressJob?.cancel()
        player.release()
        isReleased = true
    }

    private fun isPlayCompleted(): Boolean {
        return player.currentPosition >= player.duration
    }
}
