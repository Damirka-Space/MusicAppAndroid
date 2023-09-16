package space.damirka.musicappandroid.views

import androidx.compose.runtime.Composable
import coil.compose.AsyncImage
import space.damirka.musicappandroid.entities.AlbumEntity

@Composable
fun CardView(
    album: AlbumEntity
) {
    AsyncImage(
        model = album.image.url,
        contentDescription = album.title
    )
}