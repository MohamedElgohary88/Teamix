package com.chocolate.presentation.screens.organiztion

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.TeamixButton
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.screens.create_organization.navigateToCreateOrganization
import com.chocolate.presentation.screens.login.navigateToLogin
import com.chocolate.presentation.screens.organiztion.compose.SeparatorWithText
import com.chocolate.presentation.screens.welcome.navigateToWelcome
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space24
import com.chocolate.presentation.theme.Space32
import com.chocolate.presentation.theme.Space48
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.customColors
import com.chocolate.presentation.util.LocalNavController
import com.chocolate.viewmodel.organization_name.OrganizationNameInteraction
import com.chocolate.viewmodel.organization_name.OrganizationNameUiEffect
import com.chocolate.viewmodel.organization_name.OrganizationNameUiState
import com.chocolate.viewmodel.organization_name.OrganizationNameViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OrganizationScreen(
    viewModel: OrganizationNameViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                OrganizationNameUiEffect.NavigateToCreateOrganization -> navController.navigateToCreateOrganization()
                OrganizationNameUiEffect.NavigateToLoginScreen -> navController.navigateToLogin(
                    organizationName = state.organizationName
                )
            }
        }
    }
    if (state.onboardingState) {
        OrganizationContent(
            organizationNameInteraction = viewModel,
            state = state,
        )
    } else {
        LaunchedEffect(Unit) { navController.navigateToWelcome() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganizationContent(
    organizationNameInteraction: OrganizationNameInteraction,
    state: OrganizationNameUiState
) {
    val colors = MaterialTheme.customColors()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    TeamixScaffold {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_start_organization),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = Space32),
                text = stringResource(R.string.enter_your_name_organization),
                style = MaterialTheme.typography.labelMedium,
                color = colors.onBackground87
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
                    .padding(top = Space8),
                value = state.organizationName,
                onValueChange = { nameOrganization ->
                    organizationNameInteraction.onOrganizationNameChange(nameOrganization)
                },
                placeholder = { Text("", color = Color.Black.copy(alpha = 0.6f)) },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = colors.card,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = colors.black,
                    selectionColors = TextSelectionColors(
                        handleColor = colors.primary,
                        backgroundColor = colors.primary
                    )
                )
            )
            val operationFailed = stringResource(R.string.error_organization_name_empty)
            TeamixButton(
                onClick = {
                    organizationNameInteraction.onClickActionButton(state.organizationName)
                    if (state.organizationName.isBlank()) {
                        Toast.makeText(context, operationFailed, Toast.LENGTH_SHORT).show()
                    }
                    if (!state.error.isNullOrEmpty()) {
                        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Space48)
                    .padding(horizontal = Space16),
                colors = colors,
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = colors.card)
                } else {
                    Text(
                        text = stringResource(R.string.enter),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onPrimary
                    )
                }
            }
            SeparatorWithText(modifier = Modifier.padding(bottom = Space8, top = Space32))
            Text(
                text = stringResource(R.string.create_new_organizat),
                color = colors.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { organizationNameInteraction.onClickCreateOrganization() }
                    .padding(bottom = Space24),
                textAlign = TextAlign.Center
            )
        }
    }
}