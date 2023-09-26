package com.chocolate.presentation.screens.allmembers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.PersonCardWithDetails
import com.chocolate.presentation.composable.TeamixTextField
import com.chocolate.presentation.theme.SpacingXLarge
import com.chocolate.presentation.theme.SpacingXMedium
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors
import com.chocolate.viewmodel.allMembers.AllMembersUiState

@Composable
fun AllMembersScreen(

) {
    AllMembersContent(state = AllMembersUiState())
}

@Composable
private fun AllMembersContent(
    state: AllMembersUiState
) {
    val colors = MaterialTheme.customColors()

    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.background)
                .padding(SpacingXLarge),
        ) {
        TeamixTextField(
            value = state.searchInput,
            hint = stringResource(id = R.string.search),
            onValueChange = {})
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(SpacingXMedium),
            contentPadding = PaddingValues(vertical = SpacingXLarge)
        ) {
            items(state.members) { member ->
                PersonCardWithDetails(
                    personImageUrl = member.imageUrl,
                    title = member.name,
                    subTitle = member.jobTitle,
                    painter = painterResource(id = R.drawable.ic_check),
                    )
                }
            }
        }
    }

@Preview
@Composable
fun AllMembersPreview() {
    TeamixTheme {
        AllMembersContent(AllMembersUiState())
    }
}