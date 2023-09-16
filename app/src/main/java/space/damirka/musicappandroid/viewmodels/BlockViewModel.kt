package space.damirka.musicappandroid.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import space.damirka.musicappandroid.entities.BlockEntity
import space.damirka.musicappandroid.entities.BlocksEntity
import java.net.URL
import java.util.LinkedList
import kotlin.concurrent.thread

class BlockViewModel: ViewModel(){
    var data : List<BlockEntity> by mutableStateOf(LinkedList<BlockEntity>())
        private set

    init {
        thread {
            val response =  URL("https://damirka.space/api/main").readText()

            val gson = Gson()
            val blocks = gson.fromJson(response, BlocksEntity::class.java)

            data = blocks.blocks

            println(data[0].albums[0].image.url)
        }
    }

}