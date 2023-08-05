package com.chocolate.presentation.screens.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chocolate.presentation.R
import com.chocolate.presentation.screens.home.compose.HomeAppBar
import com.chocolate.presentation.screens.home.compose.ItemChannel
import com.chocolate.presentation.screens.home.compose.ItemChips
import com.chocolate.presentation.screens.home.compose.ManageChannelBottomSheet
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space64
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.customColors
import com.chocolate.viewmodel.home.HomeUiState

@Composable
fun HomeScreen() {
    HomeContent(
        state = homeUiState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeContent(state: HomeUiState) {
    val colors = MaterialTheme.customColors()
    var isShowSheet by remember { mutableStateOf(false) }


    if (isShowSheet) {
        ManageChannelBottomSheet(colors = colors) {
            isShowSheet = false
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeAppBar(state, colors) },
        contentColor = colors.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Space64),
            contentPadding = PaddingValues(vertical = Space16),
            verticalArrangement = Arrangement.spacedBy(Space8),
        ) {
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(Space8),
                    contentPadding = PaddingValues(horizontal = Space16)
                ) {
                    item {
                        ItemChips(
                            badge = state.mentionsBadge,
                            painter = painterResource(id = R.drawable.ic_mention),
                            title = "Mentions",
                            colors = colors,
                            modifier = Modifier.padding(end = Space8)
                        )
                        ItemChips(
                            badge = state.draftsBadge,
                            painter = painterResource(id = R.drawable.ic_drafts),
                            title = "Drafts",
                            colors = colors,
                            modifier = Modifier.padding(end = Space8)
                        )
                        ItemChips(
                            badge = state.starredBadge,
                            painter = painterResource(id = R.drawable.ic_star),
                            title = "Starred",
                            colors = colors,
                            modifier = Modifier.padding(end = Space8)
                        )
                        ItemChips(
                            badge = state.savedBadge,
                            painter = painterResource(id = R.drawable.ic_saved_later),
                            title = "Saved Later",
                            colors = colors
                        )
                    }
                }
            }
            item {
                Text(
                    text = "Channels",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onBackground87,
                    modifier = Modifier
                        .padding(bottom = Space8)
                        .padding(horizontal = Space16)
                )
            }
            items(items = state.channelsUIState, key = { currentChannel ->
                currentChannel.name
            }) { channelUIState ->
                ItemChannel(
                    channelUIState,
                    colors,
                    onLongClickChannel = { isShowSheet = true },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .animateItemPlacement()
                )
            }
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
fun HomePreview() {
    HomeContent(
        state = homeUiState
    )
}
