package com.ssafy.sungchef.features.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ImageTextColumnComponent(
    modifier: Modifier,
    imageResource: Any,
    text: String = "",
    size: Int = 240,
    onClick: () -> (Unit)
) {
    Card(
        modifier = modifier.clickable {
            onClick()
        },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageComponent(
                modifier = Modifier.size(size.dp),
                imageResource = imageResource
            )
            TextComponent(
                text = text,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageTextColumnComponentPreview() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageTextColumnComponent(modifier = Modifier.wrapContentSize(), imageResource = "", text = "김치찌개"){

        }
    }
}