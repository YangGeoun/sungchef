package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
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
    hintText : String,
    supportingText: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    isError : Boolean = false,
    keyboardAction : KeyboardActions = KeyboardActions()
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        colors = outlinedTextFieldColors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = Color.Red,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
            disabledBorderColor = Color.LightGray,
            errorBorderColor = Color.Red,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            disabledPlaceholderColor = MaterialTheme.colorScheme.primary,
            errorLabelColor = Color.Red,
            errorLeadingIconColor = Color.Red,
            errorTrailingIconColor = Color.Red
        ),
        label = {
            TextComponent(
                text = hintText,
                color = MaterialTheme.colorScheme.primary
            )
        },
        singleLine = singleLine,
        maxLines = maxLines,
        trailingIcon = trailingIcon,
        isError = isError,
        supportingText = supportingText,
        keyboardActions = keyboardAction
    )
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
    TextFieldComponent(
        modifier = Modifier,
        value = "",
        onValueChange = {},
        hintText = "닉네임",
    )
}