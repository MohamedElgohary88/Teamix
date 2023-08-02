package com.chocolate.presentation.screens.channel.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space32
import com.chocolate.presentation.theme.Space4
import com.chocolate.presentation.theme.Space40
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.customColors

@Composable
fun ReactionButton(
    reaction: Reaction,
    onReactionClicked:(Boolean)->Unit
) {
    var reactionCount by remember {
        mutableStateOf(reaction.count)
    }
    var reactionclicked by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .padding(vertical = Space4)
            .clip(RoundedCornerShape(Space32))
            .background(
                if (reactionclicked) MaterialTheme.customColors().gray
                else MaterialTheme.customColors().lightGray
            )
            .clickable {
                reactionclicked = !reactionclicked
                if (reactionclicked) reactionCount++ else reactionCount--
                onReactionClicked(reactionclicked)
            }
            .padding(vertical = Space4, horizontal = Space8)
    ) {
        Row() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(reaction.reaction).build(),
                contentDescription = "Reaction",
                modifier = Modifier
                    .size(Space16)
                    .padding(end = Space4)
            )
            Text(
                text = reactionCount.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.customColors().onBackground87
            )
        }
    }
}


data class Reaction(
    val reaction: Int,
    val count: Int
)