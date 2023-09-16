package space.damirka.musicappandroid.entities

data class AlbumEntity(
    val id: Int,
    val title: String,
    val description: String,

    val image: ImageEntity,

    val albumType: String,
    val imageUrl: String,
    val liked: Boolean
)
