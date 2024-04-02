package com.ssafy.sungchef.features.component

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedButtonComponent(
    modifier: Modifier = Modifier,
    text: String,
    shape: RoundedCornerShape = RoundedCornerShape(0),
    borderColor : Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = { onClick() },
        // OutlinedButton의 border 매개변수를 사용하여 테두리 색상을 설정
        border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(borderColor)),
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = borderColor // 텍스트와 아이콘 색상을 테두리 색상과 동일하게 설정
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun OutlinedButtonComponentNotMax(
    modifier: Modifier = Modifier,
    text: String,
    shape: RoundedCornerShape = RoundedCornerShape(15),
    borderColor : Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        // OutlinedButton의 border 매개변수를 사용하여 테두리 색상을 설정
        border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(borderColor)),
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = borderColor // 텍스트와 아이콘 색상을 테두리 색상과 동일하게 설정
        )
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun OutlinedButtonComponentPreview(){
    OutlinedButtonComponentNotMax(
        text = "outlined 버튼입니다.",
        shape = RoundedCornerShape(15),
        borderColor = MaterialTheme.colorScheme.primary,
        onClick = { Log.d("TAG", "OutlinedButtonComponentPreview: ")}
    )
}