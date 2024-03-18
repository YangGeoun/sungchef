package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SuggestionCardComponent(
    title: String,
    modifier: Modifier = Modifier,
    imageResource: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageComponent(modifier = modifier.size(300.dp), imageResource = imageResource)
        TextComponent(text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun SuggestionCardComponentPreview(){
    SuggestionCardComponent(title = "김치찌개", imageResource = "ㅁㄴㅇㄹ")
}