package com.chocolate.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chocolate.presentation.theme.IconSize16
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.TextFieldHeight48
import com.chocolate.presentation.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamixTextField(
    value: String,
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    hasLeadingIcon: Boolean = false,
    contentDescription: String = "",
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit) = {}
) {
    val colors = MaterialTheme.customColors()

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(TextFieldHeight48)
            .padding(0.dp),
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodySmall,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.customColors().onBackground60
            )
        },
        leadingIcon = {
            AnimatedVisibility(visible = hasLeadingIcon) {
                painter?.let {
                    Icon(
                        painter = it,
                        contentDescription = contentDescription,
                        modifier = Modifier.size(IconSize16)
                    )
                }
            }
        },
        trailingIcon = {
            trailingIcon()
        },
        shape = RoundedCornerShape(0),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = colors.card,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = colors.onBackground87,
            selectionColors = TextSelectionColors(
                handleColor = colors.primary,
                backgroundColor = colors.primary
            )
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun TeamixTextPreview() {
    TeamixTheme {
        TeamixTextField(modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(Color.Cyan), value = "a", hint = "", onValueChange = {})
    }
}