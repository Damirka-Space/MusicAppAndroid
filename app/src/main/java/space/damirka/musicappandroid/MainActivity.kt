package space.damirka.musicappandroid

import android.annotation.SuppressLint
import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.common.util.concurrent.MoreExecutors
import space.damirka.musicappandroid.services.MediaPlaybackService
import space.damirka.musicappandroid.services.PlayerService
import space.damirka.musicappandroid.ui.theme.MusicAppAndroidTheme
import space.damirka.musicappandroid.views.HomeView
import space.damirka.musicappandroid.views.PlayerView

class MainActivity : ComponentActivity() {
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStart() {
        super.onStart()

        val sessionToken = SessionToken(this, ComponentName(this, MediaPlaybackService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener({
            PlayerService.getInstance()?.setPlayer(controllerFuture.get())
        },
            MoreExecutors.directExecutor()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAppAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TabView()
                }
            }
        }
    }
}

data class TabRowItem(
    val title: String,
    val icon: ImageVector,
    val iconSel: ImageVector,
    val route: String,
    val screen: @Composable () -> Unit,
)

@Composable
fun TabScreen(
    text: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

val items = listOf(
    TabRowItem(
        title = "Home",
        screen = { HomeView() },
        icon = Icons.Outlined.Home,
        iconSel = Icons.Rounded.Home,
        route = "Home"
    ),
    TabRowItem(
        title = "Search",
        screen = { TabScreen(text = "Tab 2") },
        icon = Icons.Outlined.Search,
        iconSel = Icons.Rounded.Search,
        route = "Search"
    ),
    TabRowItem(
        title = "Favorite",
        screen = { TabScreen(text = "Tab 3") },
        icon = Icons.Outlined.FavoriteBorder,
        iconSel = Icons.Rounded.Favorite,
        route = "Favorite"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabView(
    playerService: PlayerService = PlayerService.getInstance()!!
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation (
                backgroundColor = MaterialTheme.colorScheme.background,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            if(currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                Icon(imageVector = screen.iconSel, contentDescription = screen.title)
                            } else {
                                Icon(imageVector = screen.icon, contentDescription = screen.title)
                            }
                        },
//                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NavHost(navController, startDestination = items[0].route) {
                items.forEach { item ->
                    composable(item.route) { item.screen() }
                }
            }

            if(playerService.showPlayerView()) {
                Surface(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(50.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlayerView()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MusicAppAndroidTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TabView()
        }
    }
}