package space.damirka.musicappandroid.views

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import space.damirka.musicappandroid.viewmodels.BlockViewModel


@Composable
fun HomeView(
    blocks : BlockViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Dp(30f))
        ) {
            items(blocks.data) {

                Box (
                    modifier = Modifier.fillMaxWidth()
                        .padding(Dp(10f), Dp(30f), Dp(0f), Dp(20f)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Row(
                    modifier = Modifier.horizontalScroll(
                        state = ScrollState(0),
                        enabled = true
                    )
                        .padding(Dp(10f), Dp(00f), Dp(10f), Dp(0f)),
                    horizontalArrangement = Arrangement.spacedBy(Dp(20f))
                ) {
                    it.albums.forEach {
                        CardView(album = it)
                    }
                }
            }
        }
    }
}