package space.damirka.musicappandroid.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import space.damirka.musicappandroid.R
import space.damirka.musicappandroid.entities.TrackEntity
import space.damirka.musicappandroid.factories.AlbumViewFactory
import space.damirka.musicappandroid.factories.TrackViewFactory
import space.damirka.musicappandroid.services.PlayerService
import space.damirka.musicappandroid.services.ServiceLocator
import space.damirka.musicappandroid.viewmodels.AlbumViewModel
import space.damirka.musicappandroid.viewmodels.TrackViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumView(
    id: Int,
    navController: NavHostController,
    albumViewModel: AlbumViewModel =
        viewModel(factory = AlbumViewFactory(id)),
    trackViewModel: TrackViewModel =
        viewModel(factory = TrackViewFactory(id)),

    playerService: PlayerService = PlayerService.getInstance()!!
) {
    albumViewModel.data?.let {
        val album = it
        val tracks = trackViewModel.data

        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colorScheme.background,

                    title = {
                        Text(text = album.title)
                    },

                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    },

                    elevation = 12.dp
                )
            },
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(
                        color = Color.LightGray
                    ),
            ) {
                item {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.LightGray
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(0.dp, 30.dp, 0.dp, 0.dp)
                                .background(
                                    color = Color.White
                                )
                                .width(300.dp)
                                .height(300.dp),

                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .aspectRatio(1f),
                                model = album.imageUrl,
                                contentDescription = album.title,

                            )

                        }
                    }
                }

                item {
                    Box (
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 50.dp)
                                .background(
                                    color = Color.White
                                )
                                .width(300.dp)
                                .height(130.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = album.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    maxLines = 1,
                                )
                                Text(
                                    modifier = Modifier.alpha(.8f),
                                    text = album.description,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                )

                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(30.dp, 15.dp, 30.dp, 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = {}) {
                                        Icon(imageVector = Icons.Outlined.FavoriteBorder,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .drawBehind {
                                                    drawCircle(
                                                        color = Color.LightGray,
                                                        radius = this.size.maxDimension
                                                    )
                                                }
                                        )
                                    }
                                    IconButton(onClick = {
                                        if(playerService.isPlaying() && playerService.currentAlbum() == album.id)
                                            playerService.pause()
                                        else if (playerService.currentAlbum() == album.id)
                                            playerService.play()
                                        else
                                            playerService.playAlbum(album.id, tracks)
                                    }) {
                                        if(playerService.isPlaying() && playerService.currentAlbum() == album.id) {
                                            Icon(painter = painterResource(R.drawable.baseline_pause_24),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .drawBehind {
                                                        drawCircle(
                                                            color = Color.LightGray,
                                                            radius = this.size.maxDimension
                                                        )
                                                    }
                                            )
                                        } else {
                                            Icon(painter = painterResource(R.drawable.baseline_play_arrow_24),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .drawBehind {
                                                        drawCircle(
                                                            color = Color.LightGray,
                                                            radius = this.size.maxDimension
                                                        )
                                                    }
                                            )
                                        }
                                    }
                                    IconButton(onClick = {}) {
                                        Icon(imageVector = Icons.Outlined.MoreVert,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .drawBehind {
                                                    drawCircle(
                                                        color = Color.LightGray,
                                                        radius = this.size.maxDimension
                                                    )
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                color = Color.White
                            )
                    )
                }

                itemsIndexed(tracks) { index, item ->
                    TrackView(albumId = album.id, index = index + 1, track = item, onClick = {
                        playerService.setAlbum(album.id, tracks)
                        playerService.playAt(index)
                    })
                }

                item {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(
                                color = Color.White
                            )
                    )
                }

                item {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = Color.LightGray
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun TrackView(
    albumId: Int,
    index: Int,
    track: TrackEntity,
    onClick: (Offset) -> Unit,
    playerService: PlayerService = PlayerService.getInstance()!!) {

    var color = Color.White

    if (playerService.currentAlbum() == albumId && playerService.currentIndex() + 1 == index) {
        color = Color.LightGray
    }

    Row (
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = onClick
                )
            }
            .fillMaxWidth()
            .height(55.dp)
            .background(
                color = color
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = index.toString(),
                color = Color.Black,
                modifier = Modifier.width(20.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Column {
                Text(track.title,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(track.author.joinToString(", "),
                    color = Color.Black,
                    modifier = Modifier.alpha(.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            }
        }
    }
}