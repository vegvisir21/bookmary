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
import javax.inject.Inject

@AndroidEntryPoint
class PlayerService : Service() {

    companion object {
        const val ACTION_UPDATE_METADATA = "action_update_metadata"

        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_CLOSE = "action_close"

        const val EXTRA_BOOK_TITLE = "extra_book_title"
        const val EXTRA_CHAPTER_TITLE = "extra_chapter_title"

        fun updatePlayerMetadata(
            context: Context,
            bookTitle: String,
            chapterTitle: String
        ) = startService(
            context = context,
            action = ACTION_UPDATE_METADATA,
            bookTitle = bookTitle,
            chapterTitle = chapterTitle
        )

        private fun startService(
            context: Context,
            action: String,
            bookTitle: String,
            chapterTitle: String
        ) {
            val intent = Intent(context, PlayerService::class.java).apply {
                this.action = action
                putExtra(EXTRA_BOOK_TITLE, bookTitle)
                putExtra(EXTRA_CHAPTER_TITLE, chapterTitle)
            }
            ContextCompat.startForegroundService(context, intent)
        }
    }

    @Inject
    lateinit var audioPlayer: AudioPlayer

    private val notificationId = 1001
    private val channelId = "player_channel"
    private var bookTitle: String = ""
    private var chapterTitle: String = ""

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(notificationId, buildNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action

        when (action) {
            ACTION_UPDATE_METADATA -> {
                intent?.getStringExtra(EXTRA_BOOK_TITLE)?.let { bookTitle = it }
                intent?.getStringExtra(EXTRA_CHAPTER_TITLE)?.let { chapterTitle = it }
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

    override fun onDestroy() {
        super.onDestroy()
        audioPlayer.release()
    }

    private fun buildNotification(): Notification {
        val playIntent = PendingIntent.getService(
            this, 0,
            Intent(this, PlayerService::class.java).setAction(ACTION_PLAY),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pauseIntent = PendingIntent.getService(
            this, 1,
            Intent(this, PlayerService::class.java).setAction(ACTION_PAUSE),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val closeIntent = PendingIntent.getService(
            this, 2,
            Intent(this, PlayerService::class.java).setAction(ACTION_CLOSE),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(bookTitle)
            .setContentText(chapterTitle)
            .setSmallIcon(R.drawable.ic_play) // заміни на свій
            .addAction(R.drawable.ic_pause, "Пауза", pauseIntent)
            .addAction(R.drawable.ic_play, "Відтворити", playIntent)
            .addAction(R.drawable.ic_play_next, "Закрити", closeIntent)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(notificationId, buildNotification())
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            channelId,
            "Аудіо відтворення",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

