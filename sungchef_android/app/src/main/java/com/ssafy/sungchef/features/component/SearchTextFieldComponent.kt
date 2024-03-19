package com.ssafy.sungchef.features.component

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextFieldComponent(
    modifier : Modifier = Modifier,
    value : String = "",
    shape : RoundedCornerShape = RoundedCornerShape(30),
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
        shape = shape,
        onValueChange = onValueChange,
        colors = outlinedTextFieldColors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            cursorColor = Color.Black,
            errorCursorColor = Color.Red,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            disabledBorderColor = Color.LightGray,
            errorBorderColor = Color.Red,
            focusedLabelColor = Color.Black,
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