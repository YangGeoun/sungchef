package com.ssafy.sungchef.features.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IconComponent(
    size : Int = 24,
    painter: Painter,
    contentDescription : String = "contentDescription",
    tint : Color = Color.Black
) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painter,
        contentDescription = contentDescription,
        tint = tint
    )
}


@Preview
@Composable
private fun BodyPreview() {
    IconComponent(size = 24, painter = painterResource(id = androidx.core.R.drawable.ic_call_answer))
}