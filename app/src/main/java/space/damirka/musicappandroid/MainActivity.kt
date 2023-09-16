package space.damirka.musicappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import space.damirka.musicappandroid.ui.theme.MusicAppAndroidTheme
import space.damirka.musicappandroid.views.HomeView

class MainActivity : ComponentActivity() {
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

val tabRowItems = listOf(
    TabRowItem(
        title = "Tab 1",
        screen = { HomeView() },
        icon = Icons.Outlined.Home,
        iconSel = Icons.Rounded.Home
    ),
    TabRowItem(
        title = "Tab 2",
        screen = { TabScreen(text = "Tab 2") },
        icon = Icons.Outlined.Search,
        iconSel = Icons.Rounded.Search
    ),
    TabRowItem(
        title = "Tab 3",
        screen = { TabScreen(text = "Tab 3") },
        icon = Icons.Outlined.FavoriteBorder,
        iconSel = Icons.Rounded.Favorite
    )
)

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabView() {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.background
                    )
                },
                containerColor = MaterialTheme.colorScheme.background
            ) {
                tabRowItems.forEachIndexed { index, item ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        selectedContentColor = Color.Black,
                        unselectedContentColor = MaterialTheme.colorScheme.secondary,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        icon = {
                            if(pagerState.currentPage == index) {
                                Icon(imageVector = item.iconSel, contentDescription = item.title)
                            } else {
                                Icon(imageVector = item.icon, contentDescription = item.title)
                            }
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            modifier = Modifier.padding(innerPadding),
            count = tabRowItems.size,
            state = pagerState,
            userScrollEnabled = false
        ) {
            tabRowItems[pagerState.currentPage].screen()
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