package com.chocolate.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.chocolate.presentation.theme.Float1
import com.chocolate.presentation.theme.Radius12
import com.chocolate.presentation.theme.Space0
import com.chocolate.presentation.theme.Space1
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space40
import com.chocolate.presentation.theme.Space56
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberItem(
    modifier: Modifier = Modifier,
    animateLabel: String = "",
    imageUrl: String,
    username: String,
    status: String,
    isSelected: Boolean,
    painter: Painter,
    contentDescription: String = "",
    userId: Int,
    onClickMemberItem: (Int) -> Unit
) {
    val colors = MaterialTheme.customColors()
    val cardBorderColor by animateColorAsState(
        targetValue = if (isSelected) colors.primary else Color.Unspecified,
        label = animateLabel
    )

    val cardBorderWidth by animateDpAsState(
        targetValue = if (isSelected) Space1 else Space0,
        label = animateLabel
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(Space56)
            .padding(horizontal = Space16),
        colors = CardDefaults.cardColors(colors.card),
        shape = RoundedCornerShape(Radius12),
        onClick = {onClickMemberItem(userId)},
        border = BorderStroke(width = cardBorderWidth, color = cardBorderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Space8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(Space40)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = Space8),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = username,
                    color = colors.onBackground87,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = status,
                    color = colors.onBackground60,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.weight(Float1))
            AnimatedVisibility(visible = isSelected) {
                CircularButton(containerColor = colors.primary, size = Space16) {
                    Icon(
                        painter = painter,
                        tint = colors.border,
                        contentDescription = contentDescription,
                    )
                }
            }
        }
    }
}