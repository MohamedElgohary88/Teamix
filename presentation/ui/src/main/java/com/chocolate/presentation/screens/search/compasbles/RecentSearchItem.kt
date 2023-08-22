package com.chocolate.presentation.screens.search.compasbles

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chocolate.presentation.R
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentSearchItem(text: String) {
    val colors = MaterialTheme.customColors()
    Card(onClick = { /*TODO*/ }, colors = CardDefaults.cardColors(containerColor = colors.primary)) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(Space8), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyMedium, color = colors.onPrimary)
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}