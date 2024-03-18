package com.ssafy.sungchef.features.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun SmallTextButtonComponent(
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
    SmallTextButtonComponent(text = "로그아웃", onClick = { Log.d("TAG", "BodyPreview: 로그아웃 클릭")})
}