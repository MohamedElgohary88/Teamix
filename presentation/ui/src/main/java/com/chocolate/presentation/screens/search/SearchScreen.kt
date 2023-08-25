package com.chocolate.presentation.screens.search

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.composable.TeamixTextField
import com.chocolate.presentation.screens.search.compasbles.TabScreen
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.customColors
import com.chocolate.presentation.util.CollectUiEffect
import com.chocolate.presentation.util.LocalNavController
import com.chocolate.viewmodel.search.SearchEffect
import com.chocolate.viewmodel.search.SearchInteraction
import com.chocolate.viewmodel.search.SearchUiState
import com.chocolate.viewmodel.search.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    CollectUiEffect(viewModel = viewModel) { effect ->
        when (effect) {
            is SearchEffect.NavigateToChannel -> {}
        }
    }
    SearchContent(state, viewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchContent(state: SearchUiState, searchInteraction: SearchInteraction) {
    val colors = MaterialTheme.customColors()
    TeamixScaffold(
        modifier = Modifier.fillMaxSize(),
        isDarkMode = isSystemInDarkTheme()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(colors.card)
            ) {
                TeamixTextField(
                    modifier = Modifier.padding(Space16),
                    value = state.searchQuery,
                    onValueChange = {searchInteraction.onChangeSearchQuery(it)},
                    containerColor = colors.background,
                    hint = stringResource(id = R.string.search),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = null
                        )
                    }
                )
            }
            TabScreen(
                onClickChannelItem = { searchInteraction.onClickChannelItem(it) },
                onClickMemberItem = { searchInteraction.onClickMemberItem(it) },
                state = state
            )
        }
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true
)
fun SearchPreview() {
    val viewModel: SearchViewModel = hiltViewModel()
    SearchContent(state = SearchUiState(), searchInteraction = viewModel)
}