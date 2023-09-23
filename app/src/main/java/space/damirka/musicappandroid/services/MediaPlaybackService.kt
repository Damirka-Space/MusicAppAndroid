package space.damirka.musicappandroid.services

import android.media.browse.MediaBrowser
import android.os.Bundle
import android.service.media.MediaBrowserService

class MediaPlaybackService : MediaBrowserService() {

//    private var mediaSession: MediaSessionCompat? = null
//    private lateinit var stateBuilder: PlaybackStateCompat.Builder
//
//    override fun onCreate() {
//        super.onCreate()
//
//        // Create a MediaSessionCompat
//        mediaSession = MediaSessionCompat(baseContext, LOG_TAG).apply {
//
//            // Enable callbacks from MediaButtons and TransportControls
//            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
//                    or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
//            )
//
//            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
//            stateBuilder = PlaybackStateCompat.Builder()
//                .setActions(PlaybackStateCompat.ACTION_PLAY
//                        or PlaybackStateCompat.ACTION_PLAY_PAUSE
//                )
//            setPlaybackState(stateBuilder.build())
//
//            // MySessionCallback() has methods that handle callbacks from a media controller
//            setCallback(MySessionCallback())
//
//            // Set the session's token so that client activities can communicate with it.
//            setSessionToken(sessionToken)
//        }
//    }

    override fun onGetRoot(p0: String, p1: Int, p2: Bundle?): BrowserRoot? {
        TODO("Not yet implemented")
    }

    override fun onLoadChildren(p0: String, p1: Result<MutableList<MediaBrowser.MediaItem>>) {
        TODO("Not yet implemented")
    }

}