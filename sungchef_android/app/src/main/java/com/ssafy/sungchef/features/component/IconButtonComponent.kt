package com.ssafy.sungchef.features.component

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonComponent(
    size : Int = 24,
    onClick : () -> Unit,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    painter: Painter,
    enabled : Boolean = true
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(size.dp),
        colors = colors,
        enabled = enabled
    ){
        IconComponent(painter = painter, size = size)
    }
}


@Preview
@Composable
private fun BodyPreview() {
    IconButtonComponent(size = 24, onClick = { Log.d("TAG", "BodyPreview: ")}, painter = painterResource(id = androidx.core.R.drawable.ic_call_answer))
}