package space.damirka.musicappandroid.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import space.damirka.musicappandroid.entities.AlbumEntity
import java.net.URL
import kotlin.concurrent.thread

class AlbumViewModel(private val id: Int) : ViewModel() {
    var data : AlbumEntity? by mutableStateOf(null)
        private set

    init {
        thread {
            val response =  URL("https://damirka.space/api/album/get/$id").readText()

            val gson = Gson()
            val albumEntity = gson.fromJson(response, AlbumEntity::class.java)

            data = albumEntity
        }
    }

}