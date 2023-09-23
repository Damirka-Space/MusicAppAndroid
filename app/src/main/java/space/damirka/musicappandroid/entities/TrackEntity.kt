package space.damirka.musicappandroid.entities

data class TrackEntity(
    val id: Long,
    val title: String,
    val authorId: List<Long>,
    val author: List<String>,
    val albumId: Long,
    val album: String,
    val url: String,
    val imageUrl: String,
    val metadataImageUrl: String,
    val liked: Boolean,
)
