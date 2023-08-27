package com.chocolate.presentation.screens.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.NoInternetLottie
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.screens.channel.toChannelScreen
import com.chocolate.presentation.screens.create_channel.navigateToCreateChannel
import com.chocolate.presentation.screens.home.composable.BadgeHome
import com.chocolate.presentation.screens.home.composable.ChannelItem
import com.chocolate.presentation.screens.home.composable.ManageChannelBottomSheet
import com.chocolate.presentation.screens.organiztion.navigateToOrganizationName
import com.chocolate.presentation.screens.topic_details.navigateToTopic
import com.chocolate.presentation.theme.CustomColorsPalette
import com.chocolate.presentation.theme.Float1
import com.chocolate.presentation.theme.LightPrimary
import com.chocolate.presentation.theme.OnLightPrimary
import com.chocolate.presentation.theme.Radius16
import com.chocolate.presentation.theme.SpacingLarge
import com.chocolate.presentation.theme.SpacingMedium
import com.chocolate.presentation.theme.SpacingMegaGigantic
import com.chocolate.presentation.theme.SpacingXLarge
import com.chocolate.presentation.theme.SpacingXMedium
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors
import com.chocolate.presentation.util.CollectUiEffect
import com.chocolate.presentation.util.LocalNavController
import com.chocolate.viewmodel.home.HomeInteraction
import com.chocolate.viewmodel.home.HomeUiEffect
import com.chocolate.viewmodel.home.HomeUiState
import com.chocolate.viewmodel.home.HomeViewModel
import com.chocolate.viewmodel.main.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val state by homeViewModel.state.collectAsState()

    CollectUiEffect(homeViewModel.effect) { effect ->
        when (effect) {
            is HomeUiEffect.NavigateToChannel -> {
                navController.toChannelScreen(effect.id , effect.name)
            }
            HomeUiEffect.NavigateToOrganizationName -> {
                navController.navigateToOrganizationName()
            }
            HomeUiEffect.NavigationToDrafts -> {}
            HomeUiEffect.NavigationToSavedLater -> {}
            HomeUiEffect.NavigationToStarred -> {}
            is HomeUiEffect.NavigateToTopic -> {
                navController.navigateToTopic(effect.topicName)
            }
            HomeUiEffect.NavigateToCreateChannel -> navController.navigateToCreateChannel()
        }
    }
    when {
        state.isLogged && state.showNoInternetLottie -> {
            NoInternetLottie(
                isShow = true,
                onClickRetry = homeViewModel::getData,
                isDarkMode = mainViewModel.state.value,
                text = stringResource(id = R.string.no_internet_connection)
            )
        }
        state.isLogged && state.isLoading -> LoadingColumn()
        state.isLogged -> HomeContent(state = state, homeViewModel)
        !state.isLogged->navController.navigateToOrganizationName()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeContent(state: HomeUiState, homeInteraction: HomeInteraction) {
    val colors = MaterialTheme.customColors()
    var isShowSheet by remember { mutableStateOf(false) }

    AnimatedVisibility(isShowSheet) {
        ManageChannelBottomSheet(onDismissBottomSheet = { isShowSheet = false }, colors = colors)
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = LightPrimary, darkIcons = false)

    TeamixScaffold(
        modifier = Modifier.fillMaxSize(),
        isDarkMode = isSystemInDarkTheme(),
        title = state.organizationTitle,
        imageUrl = state.imageUrl,
        hasImageUrl = true,
        titleColor = OnLightPrimary,
        hasAppBar = true,
        floatingActionButton = {
            AnimatedVisibility(visible = state.role.lowercase() == "owner") {
                FloatingActionButton(
                    onClick = {homeInteraction.onClickFloatingActionButton()},
                    containerColor = MaterialTheme.customColors().primary,
                    shape = RoundedCornerShape(Radius16),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.add_fab),
                        tint = Color.White,
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = SpacingMegaGigantic),
            contentPadding = PaddingValues(vertical = SpacingXLarge),
            verticalArrangement = Arrangement.spacedBy(SpacingXMedium),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SpacingLarge)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    CardItem(
                        badge = state.badgeCountsUiState.drafts,
                        painter = painterResource(R.drawable.ic_drafts),
                        title = stringResource(R.string.drafts),
                        colors = colors,
                        onClickItemCard = { homeInteraction.onClickDrafts() },
                        modifier = Modifier
                            .padding(horizontal = SpacingMedium)
                            .weight(Float1)
                    )

                    CardItem(
                        badge = state.badgeCountsUiState.drafts,
                        painter = painterResource(R.drawable.ic_saved_later),
                        title = stringResource(R.string.savedlater),
                        colors = colors,
                        onClickItemCard = { homeInteraction.onClickSavedLater() },
                        modifier = Modifier
                            .padding(horizontal = SpacingMedium)
                            .weight(Float1)
                    )
                }
            }
            item {
                Text(
                    text = stringResource(R.string.channels),
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onBackground87,
                    modifier = Modifier
                        .padding(top = SpacingXMedium)
                        .padding(horizontal = SpacingXLarge)
                )
            }
            items(items = state.channels, key = { currentChannel ->
                currentChannel.name
            }) { channelUIState ->
                ChannelItem(
                    channelUIState,
                    colors,
                    onClickItemChannel = {channelId , channelName->
                        homeInteraction.onClickChannel(channelId ,channelName )
                    }, onClickTopic = {
                        homeInteraction.onClickTopic(it)
                    },
                    modifier = Modifier
                        .padding(horizontal = SpacingXLarge)
                        .animateItemPlacement()
                )
            }
        }
    }
}

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    badge: Int,
    painter: Painter,
    title: String,
    colors: CustomColorsPalette,
    onClickItemCard: () -> Unit
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
                    .padding(end = SpacingMedium, top = SpacingMedium)
            )
            Icon(
                painter = painter,
                contentDescription = "icons",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = SpacingXMedium)
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
fun LoadingColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { CircularProgressIndicator(color = LightPrimary) }
}

@Composable
@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
fun HomePreview() {
    val viewModel: HomeViewModel = hiltViewModel()
    TeamixTheme {
        HomeContent(state = HomeUiState(), viewModel)
    }
}