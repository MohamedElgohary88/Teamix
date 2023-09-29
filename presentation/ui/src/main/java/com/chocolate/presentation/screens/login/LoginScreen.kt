package com.chocolate.presentation.screens.login

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.SeparatorWithText
import com.chocolate.presentation.composable.TeamixButton
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.screens.createchannel.composable.ActionSnakeBar
import com.chocolate.presentation.screens.createmember.navigateToCreateMember
import com.chocolate.presentation.screens.home.navigateToHome
import com.chocolate.presentation.screens.login.composable.LoginComponents
import com.chocolate.presentation.theme.SpacingExtraHuge
import com.chocolate.presentation.theme.SpacingGigantic
import com.chocolate.presentation.theme.SpacingSuperMassive
import com.chocolate.presentation.theme.SpacingUltraGigantic
import com.chocolate.presentation.theme.SpacingXLarge
import com.chocolate.presentation.theme.SpacingMedium
import com.chocolate.presentation.theme.SpacingHuge
import com.chocolate.presentation.theme.customColors
import com.chocolate.presentation.util.CollectUiEffect
import com.chocolate.presentation.util.LocalNavController
import com.chocolate.presentation.util.hideKeyboard
import com.chocolate.viewmodel.login.LoginInteraction
import com.chocolate.viewmodel.login.LoginUiEffect
import com.chocolate.viewmodel.login.LoginUiState
import com.chocolate.viewmodel.login.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val state by loginViewModel.state.collectAsState()
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    CollectUiEffect(loginViewModel.effect) { effect ->
        when (effect) {
            LoginUiEffect.NavigationToHome -> navController.navigateToHome()
            is LoginUiEffect.NavigateToCreateNewAccount -> navController.navigateToCreateMember(
                effect.role,
                null
            )
        }
    }
    LoginContent(loginViewModel, state, scrollState)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginContent(
    loginInteraction: LoginInteraction,
    state: LoginUiState,
    scrollState: ScrollState,
) {
    val colors = MaterialTheme.customColors()
    val context = LocalContext.current
    val rootView = LocalView.current
    TeamixScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.background)
                .padding(horizontal = SpacingXLarge)
                .verticalScroll(scrollState),
        ) {
            Text(
                modifier = Modifier.padding(top = SpacingSuperMassive),
                text = stringResource(R.string.welcome_to),
                style = MaterialTheme.typography.titleLarge,
                color = colors.onBackground87
            )
            Text(
                modifier = Modifier.padding(bottom = SpacingGigantic),
                text = state.organizationName,
                style = MaterialTheme.typography.titleLarge,
                color = colors.primary
            )

            LoginComponents(
                email = state.email,
                password = state.password,
                passwordVisibility = state.passwordVisibility,
                onClickPasswordVisibility = { loginInteraction.onClickPasswordVisibility(it) },
                onChangeEmail = { loginInteraction.onChangeEmail(it) },
                onChangePassword = { loginInteraction.onChangePassword(it) }
            )

            TeamixButton(
                onClick = {
                    hideKeyboard(context, rootView)
                    loginInteraction.onClickSignIn(state.email, state.password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SpacingHuge)
                    .height(SpacingUltraGigantic),
                colors = colors,
            ) {
                AnimatedVisibility(visible = state.isLoading) {
                    CircularProgressIndicator(
                        color = colors.card,
                        modifier = Modifier
                            .size(SpacingHuge)
                            .align(Alignment.CenterVertically)
                    )
                }
                AnimatedVisibility(visible = !state.isLoading) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onPrimary
                    )
                }
            }
            SeparatorWithText(
                modifier = Modifier.padding(
                    bottom = SpacingMedium,
                    top = SpacingExtraHuge
                )
            )
            Text(
                text = stringResource(R.string.create_new_account),
                color = colors.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { loginInteraction.onClickCreateNewAccount() })
                    .padding(bottom = SpacingHuge),
                textAlign = TextAlign.Center
            )
        }
        state.error?.let {
            ActionSnakeBar(
                contentMessage = it,
                isVisible = true,
                isToggleButtonVisible = false,
                onDismiss = {
                    loginInteraction.onSnackBarDismissed()
                }
            )
        }
    }
}