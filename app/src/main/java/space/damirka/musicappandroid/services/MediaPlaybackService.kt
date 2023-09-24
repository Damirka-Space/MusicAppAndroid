package space.damirka.musicappandroid.services

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

@UnstableApi class MediaPlaybackService : MediaSessionService(), Player.Listener {
    private var mediaSession: MediaSession? = null
    private var playerService : PlayerService = PlayerService.getInstance()!!
    private var player: ExoPlayer? = null

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player!!).build()

        setMediaNotificationProvider(DefaultMediaNotificationProvider.Builder(this).build())

        player!!.addListener(this)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        playerService.index(player!!.currentMediaItemIndex)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        playerService.playing(isPlaying)
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        playerService.repeat(repeatMode)
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession? = mediaSession
}