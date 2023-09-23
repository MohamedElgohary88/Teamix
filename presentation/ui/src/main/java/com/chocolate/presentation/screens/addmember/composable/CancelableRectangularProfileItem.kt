package com.chocolate.presentation.screens.addmember.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.CircularButton
import com.chocolate.presentation.theme.ImageSize40
import com.chocolate.presentation.theme.Radius12
import com.chocolate.presentation.theme.SpacingTiny
import com.chocolate.presentation.theme.SpacingXLarge
import com.chocolate.presentation.theme.SpacingMedium
import com.chocolate.presentation.theme.SpacingXMedium
import com.chocolate.presentation.theme.customColors

@Composable
fun CancelableRectangularProfileItem(
    personImageUrl: String,
    personName: String,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription:String?=null
) {

    Box {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Radius12),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.customColors().card),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = SpacingTiny)
        ) {
            Column(
                modifier = Modifier.padding(SpacingXLarge),
                verticalArrangement = Arrangement.spacedBy(SpacingMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = rememberAsyncImagePainter(personImageUrl),
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(ImageSize40).clip(CircleShape)
                )

                Text(
                    text = personName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.customColors().onBackground87
                )
            }
        }

        CircularButton(
            containerColor = MaterialTheme.customColors().red60,
            modifier = Modifier.align(Alignment.TopEnd).offset(y = (-SpacingXMedium), x = SpacingXMedium),
            onClick = { onCancelButtonClicked() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = contentDescription,
                tint = MaterialTheme.customColors().onPrimary,
            )
        }
    }
}
