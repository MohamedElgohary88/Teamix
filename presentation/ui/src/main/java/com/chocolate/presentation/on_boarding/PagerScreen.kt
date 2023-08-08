package com.chocolate.presentation.on_boarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chocolate.presentation.composable.FitImage
import com.chocolate.presentation.theme.customColors


@Composable
fun PagerScreen(
    onBoardingPage: OnboardingPage
) {
    Column(Modifier.fillMaxSize()) {
        FitImage(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            drawableResource = onBoardingPage.imageResource,
            contentDescription = "Onboarding image"
        )
        Text(
            modifier = Modifier.padding(top = 32.dp, start = 24.dp, end = 24.dp),
            text = onBoardingPage.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.customColors().onBackground87
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 24.dp, end = 24.dp),
            text = onBoardingPage.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.customColors().onBackground60
        )
    }
}

@Preview
@Composable
fun OnboardingOne() {
    PagerScreen(OnboardingPage.First)
}
@Preview
@Composable
fun OnboardingTwo() {
    PagerScreen(OnboardingPage.Second)
}

@Preview
@Composable
fun OnboardingThree() {
    PagerScreen(OnboardingPage.Third)
}