package com.chocolate.presentation.screens.profile.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chocolate.presentation.R
import com.chocolate.presentation.theme.Space12
import com.chocolate.presentation.theme.Space16
import com.chocolate.presentation.theme.customColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(text: String, colorFocused: Color, colorUnFocused: Color, colorIcon: Color) {
    val textState = remember {
        mutableStateOf("")
    }
    val colors = MaterialTheme.customColors()
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .padding(top = Space16),
        value = textState.value,
        onValueChange = {
            textState.value = it
        },
        placeholder = { Text(text, color = colors.onBackground60.copy(alpha = 0.6f)) },
        shape = RoundedCornerShape(Space12),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = colors.card,
            focusedBorderColor = colorFocused,
            unfocusedBorderColor = colorUnFocused
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.pen),
                contentDescription = null,
                tint = colorIcon,
            )
        }

    )
}