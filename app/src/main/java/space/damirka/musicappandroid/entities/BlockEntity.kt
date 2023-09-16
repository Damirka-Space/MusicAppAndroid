package space.damirka.musicappandroid.entities

data class BlockEntity(
    val id: Int,
    val title: String,
    val albums: List<AlbumEntity>
)
