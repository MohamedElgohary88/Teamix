package com.chocolate.presentation.screens.channel.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.ContentOptionsBottomSheet
import com.chocolate.presentation.theme.SpacingGigantic
import com.chocolate.presentation.theme.SpacingSmall
import com.chocolate.presentation.theme.SpacingXMedium
import com.chocolate.presentation.theme.SpacingXSmall
import com.chocolate.presentation.theme.SpacingXXLarge
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors
import com.chocolate.viewmodel.channel.TopicState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicCard(
    topic: TopicState,
    channelId: Int,
    onSeeAll: (Int, String, String) -> Unit,
    onSavedTopic: (TopicState) -> Unit = {}
) {
    var showSheet by remember { mutableStateOf(false) }
    AnimatedVisibility(showSheet) {
        ContentOptionsBottomSheet(onSave = { onSavedTopic(topic) }){
            showSheet = false
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = { onSeeAll(channelId, topic.id, topic.topicContent) },
                onLongClick = { showSheet = true })
            .background(color = MaterialTheme.customColors().card)
            .padding(SpacingXMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(model = topic.creatorImage),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = SpacingXMedium, vertical = SpacingXMedium)
                    .size(SpacingGigantic)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
            Column {
                Row {
                    Text(
                        text = topic.creatorName,
                        color = MaterialTheme.customColors().primary,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        modifier = Modifier.padding(start = SpacingXMedium),
                        text = topic.sentTime,
                        color = MaterialTheme.customColors().onBackground87,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Text(
                    modifier = Modifier.padding(
                        top = SpacingXSmall,
                        start = SpacingSmall,
                        end = SpacingSmall,
                    ),
                    text = topic.topicContent,
                    color = MaterialTheme.customColors().onBackground87,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }


        Icon(
            tint = MaterialTheme.customColors().onBackground87,
            modifier = Modifier
                .size(SpacingXXLarge)
                .rotate(180f),
            painter = painterResource(id = R.drawable.alt_arrow_left),
            contentDescription = topic.topicContent
        )
    }
}

@Composable
@Preview(showSystemUi = true, backgroundColor = 0x80000000, showBackground = true)
fun TopicReview() {
    TeamixTheme {
        TopicCard(
            topic = TopicState(),
            channelId = 0,
            onSeeAll = { channelId, topicId, topicName ->
            }
        )
    }
}