package space.damirka.musicappandroid.views

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import space.damirka.musicappandroid.viewmodels.BlockViewModel


@Composable
fun HomeView(
    blocks : BlockViewModel = viewModel()
) {
    val navController = rememberNavController()

   NavHost(navController = navController, startDestination = "/main") {
       composable("/main") { HomeAlbums(navController = navController, blocks) }
       composable(
           "/album/{id}",
           arguments = listOf(navArgument("id") { defaultValue = "1" })
       ) { backStackEntry ->
           backStackEntry.arguments?.getString("id")?.let {
               AlbumView(id = it.toInt(), navController)
           }
       }
   }
}

@Composable
fun HomeAlbums(
    navController: NavController,
    blocks : BlockViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(blocks.data) {

                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 30.dp, 0.dp, 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Row(
                    modifier = Modifier
                        .horizontalScroll(
                            state = rememberScrollState(0),
                            enabled = true
                        )
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    it.albums.forEach {
                        val album = it
                        CardView(album = album,
                        onClick = {
                            navController.navigate("/album/" + album.id)
                        })
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}