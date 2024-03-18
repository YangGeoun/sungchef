package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldComponent(
    modifier : Modifier = Modifier,
    value : String = "",
    onValueChange: (String) -> Unit,
    hintText : String
) {
    OutlinedTextField(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        colors = outlinedTextFieldColors(
            cursorColor = Color.Blue,
            errorCursorColor = Color.Red,
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.LightGray,
            errorBorderColor = Color.Red,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Gray,
            disabledPlaceholderColor = Color.Gray,
            errorLabelColor = Color.Red,
            errorLeadingIconColor = Color.Red,
            errorTrailingIconColor = Color.Red
        ),
        label = {
            TextComponent(
                text = hintText,
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
    TextFieldComponent(
        modifier = Modifier,
        value = "",
        onValueChange = {},
        hintText = "닉네임"
    )
}