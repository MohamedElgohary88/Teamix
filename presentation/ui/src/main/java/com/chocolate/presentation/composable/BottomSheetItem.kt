package com.chocolate.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.Space8
import com.chocolate.presentation.theme.customColors

@Composable
fun BottomSheetItem(
    icon:Painter,
    text:String,
    onClickItem:()->Unit = {},
    contentDescription:String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onClickItem()}
            .padding(Space16),
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = Space8)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.customColors().onBackground60
        )
    }
}
