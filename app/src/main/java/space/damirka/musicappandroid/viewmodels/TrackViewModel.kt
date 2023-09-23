package space.damirka.musicappandroid.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import space.damirka.musicappandroid.entities.AlbumEntity
import space.damirka.musicappandroid.entities.TrackEntity
import java.net.URL
import java.util.LinkedList
import kotlin.concurrent.thread

class TrackViewModel(private val id: Int) : ViewModel() {
    var data : List<TrackEntity> by mutableStateOf(LinkedList<TrackEntity>())
        private set

    init {
        thread {
            val response =  URL("https://damirka.space/api/album/tracks/get/$id").readText()

            val gson = Gson()
            val albumEntity = gson.fromJson(response, Array<TrackEntity>::class.java)

            data = albumEntity.toList()
        }
    }

}