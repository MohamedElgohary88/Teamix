//package com.chocolate.presentation.composable
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.combinedClickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.constraintlayout.compose.ConstraintLayout
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.chocolate.presentation.theme.SpacingMedium
//import com.chocolate.presentation.theme.SpacingTiny
//import com.chocolate.presentation.theme.SpacingXMedium
//import com.chocolate.presentation.theme.SpacingXXLarge
//import com.chocolate.presentation.theme.customColors
//import com.chocolate.viewmodel.topicmessages.MessageUiState
//import com.chocolate.viewmodel.topicmessages.ReactionUiState
//
//@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
//@Composable
//fun MyReplyMessage(
//    modifier: Modifier = Modifier,
//    messageUiState: MessageUiState,
//    onAddReactionToMessage: (Int) -> Unit,
//    onSaveMessage: () -> Unit,
//    onGetNotification: () -> Unit,
//    onPinMessage: () -> Unit,
//    onClickReact: (Boolean, ReactionUiState) -> Unit,
//) {
//    var showSheet by remember { mutableStateOf(false) }
//
//    AnimatedVisibility (showSheet) {
//        MessageOptionsBottomSheet(
//
//            onSaveMessage =onSaveMessage
//        ){
//            showSheet = false
//        }
//    }
//    ConstraintLayout(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(
//                end = SpacingTiny,
//                start = SpacingXXLarge,
//            )
//            .padding(horizontal = SpacingXMedium)
//    ) {
//        val (image, messageCard, emojis) = createRefs()
//
//        Card(colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.customColors().primary
//        ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentSize(align = Alignment.CenterEnd)
//                .padding(
//                    end = 0.dp
//                )
//                .constrainAs(messageCard) {
//                    end.linkTo(parent.end)
//                }
//                .clip(
//                    RoundedCornerShape(
//                        topStart = 12.dp,
//                        topEnd = 12.dp,
//                        bottomEnd =  0.dp,
//                        bottomStart = 12.dp
//                    )
//                )
//                .combinedClickable(onClick = {}, onLongClick = {
//                    showSheet = true
//                }),
//            shape = RoundedCornerShape(
//                topStart = 12.dp,
//                topEnd = 12.dp,
//                bottomEnd = 0.dp,
//                bottomStart =12.dp
//            )
//        ) {
//            Column(
//                modifier = Modifier.padding(SpacingXMedium)
//            ) {
//                AnimatedVisibility (messageUiState.messageImageUrl.isNotEmpty()) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current).data(messageUiState.messageImageUrl)
//                            .build(),
//                        modifier = Modifier
//                            .padding(bottom = SpacingMedium)
//                            .fillMaxWidth()
//                            .height(150.dp)
//                            .clip(RoundedCornerShape(SpacingXMedium)),
//                        contentDescription = "",
//                        contentScale = ContentScale.FillWidth
//                    )
//                }
//
//                Text(
//                    textAlign = TextAlign.End,
//                    text = messageUiState.message,
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.White
//                )
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = SpacingMedium),
//                    textAlign = TextAlign.End,
//                    text = messageUiState.replayDate,
//                    style = MaterialTheme.typography.bodySmall,
//                    color =  Color.White
//                )
//            }
//
//        }
//
//        AnimatedVisibility (messageUiState.reactions.isNotEmpty()) {
//            FlowRow(horizontalArrangement = Arrangement.spacedBy(SpacingXMedium),
//                modifier = Modifier.constrainAs(emojis) {
//                    start.linkTo(image.end)
//                    top.linkTo(messageCard.bottom)
//                }) {
//                messageUiState.reactions.forEach { reaction ->
//                    ReactionButton(reaction) { clicked, reaction ->
//                        onClickReact(clicked,reaction)
//                    }
//                }
//            }
//        }
//    }
//
//}