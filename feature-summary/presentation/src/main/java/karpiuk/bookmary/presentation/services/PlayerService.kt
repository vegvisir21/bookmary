package karpiuk.bookmary.presentation.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import karpiuk.bookmary.core_domain.tools.AudioPlayer
import karpiuk.bookmary.core_ui.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlayerService : Service() {

    companion object {
        const val ACTION_UPDATE_METADATA = "action_update_metadata"
        const val ACTION_UPDATE_CLOSABILITY = "action_update_closability"

        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_CLOSE = "action_close"

        const val EXTRA_BOOK_TITLE = "extra_book_title"
        const val EXTRA_CHAPTER_TITLE = "extra_chapter_title"
        const val EXTRA_IS_CLOSABLE = "extra_is_closable"

        fun updatePlayerMetadata(
            context: Context,
            bookTitle: String,
            chapterTitle: String
        ) = startService(
            context = context,
            intent = Intent(context, PlayerService::class.java).apply {
                action = ACTION_UPDATE_METADATA
                putExtra(EXTRA_BOOK_TITLE, bookTitle)
                putExtra(EXTRA_CHAPTER_TITLE, chapterTitle)
            }
        )

        fun updateClosability(
            context: Context,
            isClosable: Boolean
        ) = startService (
            context = context,
            intent = Intent(context, PlayerService::class.java).apply {
                action = ACTION_UPDATE_CLOSABILITY
                putExtra(EXTRA_IS_CLOSABLE, isClosable)
            }
        )

        private fun startService(
            context: Context,
            intent: Intent,
        ) = ContextCompat.startForegroundService(context, intent)
    }

    @Inject
    lateinit var audioPlayer: AudioPlayer

    private val notificationId = 1001
    private val channelId = "player_channel"
    private var bookTitle: String = ""
    private var chapterTitle: String = ""
    private var isClosable: Boolean = false

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var isPlayingJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(notificationId, buildNotification())
        observeIsPlaying()
    }

    private fun observeIsPlaying() {
        isPlayingJob?.cancel()
        isPlayingJob = serviceScope.launch {
            audioPlayer.isPlaying.collect { isPlaying ->
                updateNotification()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action

        when (action) {
            ACTION_UPDATE_METADATA -> {
                intent.getStringExtra(EXTRA_BOOK_TITLE)?.let { bookTitle = it }
                intent.getStringExtra(EXTRA_CHAPTER_TITLE)?.let { chapterTitle = it }
                updateNotification()
            }
            ACTION_UPDATE_CLOSABILITY -> {
                intent.getBooleanExtra(
                    EXTRA_IS_CLOSABLE,
                    false
                ).let { isClosable = it }
                updateNotification()
            }
            ACTION_PLAY -> audioPlayer.play()
            ACTION_PAUSE -> audioPlayer.pause()
            ACTION_CLOSE -> {
                audioPlayer.release()
                stopSelf()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(): Notification {
        val isPlaying = audioPlayer.isPlaying.value

        val packageManager = this.packageManager
        val intent = packageManager.getLaunchIntentForPackage(this.packageName)?.apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val playPauseIntent = PendingIntent.getService(
            this, 0,
            Intent(this, PlayerService::class.java).apply {
                action = if (isPlaying) ACTION_PAUSE else ACTION_PLAY
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val playPauseIcon = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        val playPauseText = if (isPlaying) "Pause" else "Play"

        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(bookTitle)
            .setContentText(chapterTitle)
            .setSmallIcon(playPauseIcon)
            .addAction(playPauseIcon, playPauseText, playPauseIntent)
            //TODO if app was closed - should open Summary screen with restoring the state
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setAutoCancel(!isClosable)
            .setOngoing(isClosable)

        if (isClosable) {
            val closeIntent = PendingIntent.getService(
                this, 2,
                Intent(this, PlayerService::class.java).setAction(ACTION_CLOSE),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            builder.addAction(R.drawable.ic_play_next, "Close", closeIntent)
        }

        return builder.build()
    }

    private fun updateNotification() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(notificationId, buildNotification())
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            "Audio output",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        isPlayingJob?.cancel()
        audioPlayer.release()
    }
}

