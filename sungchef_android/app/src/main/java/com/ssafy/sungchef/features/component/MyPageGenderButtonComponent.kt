package com.ssafy.sungchef.features.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R

/**
 * modifier : Modifier
 * gender : 남성, 여성
 * genderResource : drawable 안에 있는 남성 여성 이미지
 * color : 남성 primary 여성 primaryContainer
 * shape : 입맛에 맞게 변경
 */
@Composable
fun MyPageGenderButtonComponent(
    gender : String,
    genderResource : Int,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    modifier : Modifier = Modifier,
    shape : RoundedCornerShape = RoundedCornerShape(30),
) {
    Row(
        modifier = modifier
            .width(180.dp)
            .height(60.dp)
            .background(
                color = color,
                shape = shape
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextComponent(
            modifier = Modifier.padding(start = 10.dp),
            text = gender,
            fontSize = 28.sp,
        )
        ImageComponent(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 10.dp),
            imageResource = genderResource
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
    MyPageGenderButtonComponent(
        "여성",
        R.drawable.gender_woman,
    )
}