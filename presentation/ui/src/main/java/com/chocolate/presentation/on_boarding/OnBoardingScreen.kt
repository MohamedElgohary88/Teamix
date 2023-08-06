package com.chocolate.presentation.on_boarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chocolate.presentation.composable.Button
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    navController: NavController,
) {
    // navigate To Home here using the navController
    OnBoardingContent({  })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingContent(
    navigateToHome: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val onboardingPages = listOf(OnBoardingPage.First, OnBoardingPage.Second, OnBoardingPage.Third)
    val colors = MaterialTheme.customColors()
    Column(Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(8f),
            pageCount = onboardingPages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPage = onboardingPages[position])
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .weight(2f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PageIndicator(
                numberOfPages = onboardingPages.size,
                modifier = Modifier.padding(),
                selectedColor = colors.primary,
                defaultColor = colors.onSecondary,
                space = 8.dp,
                defaultRadius = 8.dp,
                selectedLength = 36.dp,
                selectedPage = pagerState.currentPage
            )
            Button(
                onClick = {
                    if (pagerState.currentPage != 2) {
                        coroutineScope.launch {
                            pagerState.scrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        navigateToHome()
                    }
                },
                modifier = Modifier,
                colors = colors
            ) {
                Text(
                    text = if (pagerState.currentPage != 2) "Next" else "Lets start",
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.onPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun OnBoardingScreenPreview() {
    TeamixTheme {
        OnBoardingScreen(navController = rememberNavController())
    }
}

