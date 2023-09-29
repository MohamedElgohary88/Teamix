package com.chocolate.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.chocolate.presentation.theme.Height76
import com.chocolate.presentation.theme.ImageSize40
import com.chocolate.presentation.theme.Radius12
import com.chocolate.presentation.theme.SpacingMedium
import com.chocolate.presentation.theme.SpacingXXLarge
import com.chocolate.presentation.theme.SpacingXXSmall
import com.chocolate.presentation.theme.Width85
import com.chocolate.presentation.theme.Width90
import com.chocolate.presentation.theme.customColors

@Composable
fun SelectedMemberItem(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    painter: Painter,
    imageUrl: String,
    username: String,
    userId: String,
    onClickIcon: (String) -> Unit
) {
    val colors = MaterialTheme.customColors()
    Box(modifier = modifier
        .width(Width90)
        .height(Height76)) {
        Card(
            colors = CardDefaults.cardColors(colors.card),
            shape = RoundedCornerShape(Radius12),
            modifier = Modifier
                .width(Width85)
                .wrapContentHeight()
                .align(Alignment.Center)
        ) {
            Spacer(modifier = Modifier.height(SpacingMedium))
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(ImageSize40)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Text(
                text = username,
                style = MaterialTheme.typography.labelSmall,
                color = colors.onBackground87,
                modifier = Modifier
                    .padding(horizontal = SpacingMedium)
                    .padding(top = SpacingXXSmall, bottom = SpacingMedium)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .size(SpacingXXLarge)
                    .clip(CircleShape)
                    .background(colors.red60)
                    .clickable { onClickIcon(userId) },
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    painter = painter,
                    tint = colors.border,
                    contentDescription = contentDescription,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}