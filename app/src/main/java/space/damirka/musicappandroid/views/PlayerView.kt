package space.damirka.musicappandroid.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import space.damirka.musicappandroid.R
import space.damirka.musicappandroid.services.PlayerService

@Composable
fun PlayerView(
    playerService: PlayerService = PlayerService.getInstance()!!
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp),
            onClick = {}) {
            Icon(imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
            )
        }

        val track = playerService.currentTrack()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = track.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Text(text = track.author.joinToString(", "),
                modifier = Modifier.alpha(.9f),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }

        IconButton(
            modifier = Modifier.padding(10.dp, 0.dp, 10.dp, 0.dp),
            onClick = {
                if(playerService.isPlaying())
                    playerService.pause()
                else
                    playerService.play()
            }
        ) {
            if(playerService.isPlaying()) {
                Icon(painter = painterResource(R.drawable.baseline_pause_24),
                    contentDescription = null,
                )
            } else {
                Icon(painter = painterResource(R.drawable.baseline_play_arrow_24),
                    contentDescription = null,
                )
            }
        }
    }
}