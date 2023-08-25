package com.chocolate.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chocolate.presentation.R
import com.chocolate.viewmodel.channel.TopicState
import com.chocolate.viewmodel.topic.ReactionUiState
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space4
import com.chocolate.presentation.theme.Space40
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Topic(
    topicState: TopicState,
    onClickReact: (Boolean, ReactionUiState) -> Unit,
    onOpenReactTile: () -> Unit,
    onSeeAll: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(topicState.creatorImage)
                .build(),
            modifier = Modifier
                .padding(end = Space4)
                .clip(CircleShape)
                .size(Space40),
            contentDescription = ""
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.customColors().card)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Space8),
                    verticalArrangement = Arrangement.spacedBy(Space8)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = topicState.creatorName,
                            color = MaterialTheme.customColors().onBackground87,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = topicState.topicCreationDate.toString(),
                            color = MaterialTheme.customColors().onBackground60,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Text(
                        text = topicState.topicName,
                        color = MaterialTheme.customColors().onBackground60,
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (!topicState.reactions.isEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(Space8),
                            modifier = Modifier
                        ) {
                            topicState.reactions.forEach { reaction ->
                                ReactionButton(reaction) { clicked, reaction ->
                                    onClickReact(clicked, reaction)
                                }
                            }
                            Box(contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(vertical = Space4)
                                    .clip(RoundedCornerShape(100.dp))
                                    .background(
                                        MaterialTheme.customColors().lightGray
                                    )
                                    .clickable {
                                        onOpenReactTile()
                                    }
                                    .padding(vertical = Space4, horizontal = Space8)) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(R.drawable.add_reaction).build(),
                                    contentDescription = "Reaction",
                                    modifier = Modifier.size(Space16)
                                )
                            }
                        }
                    }

                    if (!topicState.replayImages.isEmpty()) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Space4)
                        ) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(-Space4),
                                reverseLayout = true
                            ) {
                                if (topicState.replayImages.size <= 4) {
                                    items(topicState.replayImages.size) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(topicState.replayImages[it])
                                                .build(),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .border(
                                                    width = 1.dp,
                                                    color = MaterialTheme.customColors().card,
                                                    shape = CircleShape
                                                )
                                                .size(18.dp),
                                            contentDescription = ""
                                        )
                                    }
                                } else {
                                    item {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .background(MaterialTheme.customColors().primary)
                                                .size(18.dp),
                                        ) {
                                            Text(
                                                text = "+${topicState.replayImages.size - 4}",
                                                color = Color.White,
                                                fontSize = 6.sp
                                            )
                                        }
                                    }
                                    items(4) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(topicState.replayImages[it])
                                                .build(),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .border(
                                                    width = 1.dp,
                                                    color = MaterialTheme.customColors().card,
                                                    shape = CircleShape
                                                )
                                                .size(18.dp),
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "Replies",
                                color = MaterialTheme.customColors().primary,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }

                }

                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Space4)
                        .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                        .background(MaterialTheme.customColors().primary)
                        .clickable {
                            onSeeAll()
                        }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Space4),
                        modifier = Modifier
                            .padding(vertical = Space4, horizontal = Space8)
                    ) {
                        Text(
                            text = stringResource(R.string.see_all),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                        Icon(
                            tint = Color.White,
                            modifier = Modifier
                                .size(14.dp)
                                .rotate(180f),
                            painter = painterResource(id = R.drawable.alt_arrow_left),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showSystemUi = true)
fun TopicReview() {
    TeamixTheme {
        Topic(
            topicState = TopicState(),
            onClickReact = { clicked, react -> },
            onOpenReactTile = {},
            onSeeAll = {}
        )
    }
}