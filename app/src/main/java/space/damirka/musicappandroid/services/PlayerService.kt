package space.damirka.musicappandroid.services

import android.app.Notification
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import space.damirka.musicappandroid.entities.TrackEntity
import java.util.*
import kotlin.concurrent.thread

class PlayerService {

    private var playing by mutableStateOf(false)

    private var albumId by mutableStateOf(-1)

    private var index by mutableStateOf(-1)

    private var tracks : List<TrackEntity> = LinkedList()

    companion object {
        private var INSTANCE: PlayerService? = null

        fun getInstance(): PlayerService? {

            if(INSTANCE == null) {
                INSTANCE = PlayerService()
            }

            return INSTANCE
        }
    }

    private var mediaController: MediaController? = null

    fun setPlayer(mediaController: MediaController) {
        this.mediaController = mediaController
    }

    fun playAlbum(albumId: Int, album: List<TrackEntity>) {
        setAlbum(albumId, album)

        playAt(0)
    }

    fun setAlbum(albumId: Int, album: List<TrackEntity>) {
        this.albumId = albumId
        this.tracks = album

        mediaController?.clearMediaItems()

        val items = LinkedList<MediaItem>()

        album.forEach { track ->
            val item = MediaItem
                .Builder()
                .setUri(Uri.parse(track.url))
                .setMediaMetadata(MediaMetadata.Builder()
                    .setAlbumTitle(track.album)
                    .setTitle(track.title)
                    .setArtist(track.author.joinToString(", "))
                    .setArtworkUri(Uri.parse(track.metadataImageUrl))
                    .build())
                .build()

            items.add(item)
        }

        mediaController?.setMediaItems(items)
    }

    fun isPlaying(): Boolean {
        return playing
    }

    fun currentAlbum(): Int {
        return albumId
    }

    fun currentIndex(): Int {
        return index
    }

    fun currentTrack(): TrackEntity {
        return tracks[index]
    }

    fun playAt(i: Int) {
        if(i > tracks.size)
            index = 0
        else if(i < 0)
            index = tracks.size - 1
        else
            index = i

//        val track = tracks[index]
//
//        val item = MediaItem
//            .Builder()
//            .setUri(Uri.parse(track.url))
//            .setMediaMetadata(MediaMetadata.Builder()
//                .setAlbumTitle(track.album)
//                .setTitle(track.title)
//                .setArtist(track.author.joinToString(", "))
//                .setArtworkUri(Uri.parse(track.metadataImageUrl))
//                .build())
//            .build()

        mediaController?.seekTo(index, 0)
        mediaController?.prepare()

        play()
    }

    fun play() {
        playing = true
        mediaController?.play()
    }

    fun pause() {
        playing = false
        mediaController?.pause()
    }
}