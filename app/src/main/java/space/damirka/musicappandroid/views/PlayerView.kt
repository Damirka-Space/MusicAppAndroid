package space.damirka.musicappandroid.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import space.damirka.musicappandroid.R
import space.damirka.musicappandroid.services.PlayerService

@Composable
fun PlayerView(
    playerService: PlayerService = PlayerService.getInstance()!!
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ProgressBar()
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
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
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Text(text = track.author.joinToString(", "),
                    modifier = Modifier.alpha(.9f),
                    style = MaterialTheme.typography.bodySmall,
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
}

/** Iterate the progress value */
suspend fun loadProgress(updateProgress: (Long, Long) -> Unit) {
    val player = PlayerService.getInstance()

    while (true) {
        val position = player?.position()
        val duration = player?.duration()

        duration?.let {
            updateProgress(position!!, duration)
        }
        delay(1000)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProgressBar() {
    var currentProgress by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    scope.launch {
        loadProgress { position, duration ->
            currentProgress = position.toFloat().div(duration)
        }
    }

    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progress = currentProgress,
        color = Color.Black
    )
}