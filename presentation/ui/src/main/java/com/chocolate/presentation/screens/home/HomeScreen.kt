package com.chocolate.presentation.screens.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chocolate.presentation.R
import com.chocolate.presentation.screens.home.compose.BadgeHome
import com.chocolate.presentation.screens.home.compose.ChannelItem
import com.chocolate.presentation.screens.home.compose.ManageChannelBottomSheet
import com.chocolate.presentation.screens.home.compose.NoInternetLottie
import com.chocolate.presentation.screens.home.compose.TeamixTopAppBar
import com.chocolate.presentation.screens.organiztion.navigateToOrganizationName
import com.chocolate.presentation.theme.CustomColorsPalette
import com.chocolate.presentation.theme.LightPrimary
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space4
import com.chocolate.presentation.theme.Space64
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors
import com.chocolate.viewmodel.home.HomeUiState
import com.chocolate.viewmodel.home.HomeViewModel
import com.chocolate.viewmodel.main.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(color = LightPrimary, darkIcons = false)
        systemUiController.setNavigationBarColor(Color.Black)
        onDispose {
            systemUiController.setNavigationBarColor(Color.Black)
            systemUiController.setSystemBarsColor(color = LightPrimary, darkIcons = false)
        }
    }

    val state by homeViewModel.state.collectAsState()
    val context = LocalContext.current
    if (state.isLogged) {
        if (state.error != null) {
            NoInternetLottie(
                isShow = true,
                onClickRetry = homeViewModel::getData,
                isDarkMode = mainViewModel.state.value,
                )
        } else {
            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = LightPrimary)
                    }
                }

                else -> {
                    HomeContent(
                        state = state,
                        navigationToDrafts = {
                            Toast.makeText(context, "Drafts Coming soon!", Toast.LENGTH_SHORT)
                                .show()
                        },
                        navigationToSavedLater = {
                            Toast.makeText(context, "Saved Later Coming soon!", Toast.LENGTH_SHORT)
                                .show()
                        },
                        navigateToChannel = {
                            Toast.makeText(
                                context,
                                "Navigate to channel: $it ",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        },
                        navigateToTopic = {
                            Toast.makeText(
                                context,
                                "Navigate to Topic: $it ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    } else {
        LaunchedEffect(Unit) { navController.navigateToOrganizationName() }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeContent(
    state: HomeUiState,
    navigationToDrafts: () -> Unit,
    navigateToTopic: (String) -> Unit,
    navigationToSavedLater: () -> Unit,
    navigateToChannel: (Int) -> Unit
) {
    val context = LocalContext.current
    val colors = MaterialTheme.customColors()
    var isShowSheet by remember { mutableStateOf(false) }

    if (isShowSheet) {
        ManageChannelBottomSheet(onDismissBottomSheet = { isShowSheet = false }, colors = colors)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TeamixTopAppBar(
                imageUrl = state.imageUrl,
                title = state.organizationTitle,
                colors = colors
            )
        },
        containerColor = colors.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Space64),
            contentPadding = PaddingValues(vertical = Space16),
            verticalArrangement = Arrangement.spacedBy(Space8),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    CardItem(
                        badge = state.badgeCountsUiState.drafts,
                        painter = painterResource(R.drawable.ic_drafts),
                        title = "Drafts",
                        colors = colors,
                        onClickItemCard = { navigationToDrafts() },
                        modifier = Modifier
                            .padding(horizontal = Space4)
                            .weight(1f)
                    )

                    CardItem(
                        badge = state.badgeCountsUiState.drafts,
                        painter = painterResource(R.drawable.ic_saved_later),
                        title = "SavedLater",
                        colors = colors,
                        onClickItemCard = { navigationToSavedLater() },
                        modifier = Modifier
                            .padding(horizontal = Space4)
                            .weight(1f)
                    )
                }
            }
            item {
                Text(
                    text = stringResource(R.string.channels),
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onBackground87,
                    modifier = Modifier
                        .padding(top = Space8)
                        .padding(horizontal = Space16)
                )
            }
            items(items = state.channels, key = { currentChannel ->
                currentChannel.name
            }) { channelUIState ->
                ChannelItem(
                    channelUIState,
                    colors,
                    onClickItemChannel = {
                        navigateToChannel(channelUIState.channelId)
                    }, onClickTopic = {
                        navigateToTopic(it)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .animateItemPlacement()
                )
            }
        }
    }
}

@Composable
private fun CardItem(
    badge: Int,
    painter: Painter,
    title: String,
    colors: CustomColorsPalette,
    onClickItemCard: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(140.dp)
            .height(96.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.card)
            .clickable { onClickItemCard() },
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            BadgeHome(
                number = badge,
                textColor = colors.onPrimary,
                cardColor = colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(end = Space4, top = Space4)
            )
            Icon(
                painter = painter,
                contentDescription = "icons",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = Space8)
                    .align(Alignment.CenterHorizontally),
                tint = colors.onBackground60,
            )
            Text(
                text = title,
                color = colors.onBackground60,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
fun HomePreview() {
    TeamixTheme {
        HomeContent(
            state = HomeUiState(),
            navigationToDrafts = {},
            navigationToSavedLater = {},
            navigateToChannel = {},
            navigateToTopic = {}
        )
    }
}