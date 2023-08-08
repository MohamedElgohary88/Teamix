package com.chocolate.presentation.screens.messageSearch

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
import com.chocolate.presentation.screens.combosable.PersonCardWithDetails
import com.chocolate.presentation.screens.combosable.SearchBox
import com.chocolate.presentation.screens.searchMessage
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors
import com.chocolate.viewmodel.messageSearch.MessageSearchUiState

@Composable
fun MessageSearchScreen(
    //navController: NavController,
) {
    MessageSearchContent(searchMessage)
}

@Composable
private fun MessageSearchContent(
    state: MessageSearchUiState
) {
    val colors = MaterialTheme.customColors()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.background)
                .padding(Space16),
        ) {
            SearchBox()
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Space8),
                contentPadding = PaddingValues(vertical = Space16)
            ) {
                items(state.messages) { message ->
                    PersonCardWithDetails(
                        personImageUrl = message.imageUrl,
                        title = message.name,
                        subTitle = message.messageContent,
                        date = message.date,
                        subTitleMaxLine = 3
                    )
                }
            }
        }
    }
