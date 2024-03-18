package com.ssafy.sungchef.features.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.R


@Composable
fun SmallClickTextComponent(
    text : String,
    size : Int = 16,
    color : Color = MaterialTheme.colorScheme.primary,
    onClick : () -> Unit = {},

    ) {
    Text(
        text = text,
        style = TextStyle(fontSize = size.sp, color = color),
        modifier = Modifier.clickable {
//            Log.d("TAG", "SmallClickTextComponent: ${onClick}")
            onClick()
        }
    )
}


@Preview
@Composable
private fun BodyPreview() {
    SmallClickTextComponent(text = "로그아웃", onClick = { Log.d("TAG", "BodyPreview: 로그아웃 클릭")})
}