package com.chocolate.presentation.screens.allMembers.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.chocolate.presentation.theme.ImageSize40
import com.chocolate.presentation.theme.Radius12
import com.chocolate.presentation.theme.Space0
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.customColors


@Composable
fun MemberItem(
    personImageUrl: String,
    name: String,
    jobTitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(Radius12),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.customColors().card),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = Space0)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Space8)
        ) {
            Image(
                painter = rememberAsyncImagePainter(personImageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(ImageSize40)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier.padding(horizontal = Space8),
                verticalArrangement = Arrangement.spacedBy(Space8)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.customColors().onBackground87
                )
                Text(
                    text = jobTitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.customColors().onBackground60
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemberItemPev() {
    // MemberItem(painterResource(id = R.drawable.test_peson_image),"Ali Mohammed", "Android Developer")
}