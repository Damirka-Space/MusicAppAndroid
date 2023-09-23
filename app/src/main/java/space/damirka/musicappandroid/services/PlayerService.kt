package space.damirka.musicappandroid.services

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import space.damirka.musicappandroid.entities.TrackEntity
import java.util.*
import kotlin.concurrent.thread

class PlayerService {

    private var playing by mutableStateOf(false)

    private var albumId by mutableStateOf(-1)

    private var index by mutableStateOf(-1)

    private var tracks : List<TrackEntity> = LinkedList()

    private var mediaPlayer: MediaPlayer? = null

    companion object {
        private var INSTANCE: PlayerService? = null

        fun getInstance(): PlayerService? {

            if(INSTANCE == null) {
                INSTANCE = PlayerService()
            }

            return INSTANCE
        }
    }

    fun playAlbum(albumId: Int, album: List<TrackEntity>) {
        setAlbum(albumId, album)

        playAt(0)
    }

    fun setAlbum(albumId: Int, album: List<TrackEntity>) {
        this.albumId = albumId
        this.tracks = album
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

        val track = tracks[index]

        thread {
            this.mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(track.url)
                prepare()
                play()
            }
        }
    }

    fun play() {
        playing = true
        mediaPlayer?.start()
    }

    fun pause() {
        playing = false
        mediaPlayer?.pause()
    }
}