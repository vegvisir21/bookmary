package karpiuk.bookmary.core_player.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import karpiuk.bookmary.core_domain.tools.AudioPlayer
import karpiuk.bookmary.core_player.ExoAudioPlayer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    @Singleton
    fun provideAudioPlayer(@ApplicationContext context: Context): AudioPlayer {
        return ExoAudioPlayer(context)
    }
}