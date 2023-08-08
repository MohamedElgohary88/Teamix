package com.chocolate.presentation.profile.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chocolate.presentation.theme.customColors

@Composable
fun MultiChoiceDialog(onClick: () -> Unit, list: List<String>) {
    val (selected) = list.map {
        remember {
            mutableStateOf(list[1])
        }
    }
    val color = MaterialTheme.customColors()
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            onClick()
        },
        confirmButton = {},
        dismissButton = {},
        text = {
            Column(
                modifier = Modifier
                    .size(width = 300.dp, height = 350.dp)
                    .clip(RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                list.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = text)
                        RadioButton(selected = text == selected.value, onClick = {
                            selected.value = text
                        })

                    }
                }
            }

        }, containerColor = color.background
    )

}

@Preview(showBackground = true)
@Composable
fun MultiChoiceDialogPreview() {
    MultiChoiceDialog({}, emptyList())
}
