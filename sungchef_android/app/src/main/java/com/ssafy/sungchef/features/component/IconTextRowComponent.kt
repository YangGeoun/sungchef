package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R

@Composable
fun IconTextRowComponent(
    modifier: Modifier = Modifier,
    size: Int = 24,
    painter:Painter,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconComponent(painter = painter, size = size)
        Spacer(modifier = modifier.size(2.dp))
        TextComponent(
            text = text,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IconTextRowComponentPreview(){
    IconTextRowComponent(painter = painterResource(id = R.drawable.timer), size = 15, text = "15분 이내")
}