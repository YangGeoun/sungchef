package com.ssafy.sungchef.features.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.R

/**
 * ingredientName : 재료 이름
 * modifier
 * selected : 라디오 버튼이 선택되었는지
 * ingredientType : 재료 분류 ENUM 값
 * onSelect : 라디오 버튼 클릭 시 기능 정의
 */
@Composable
fun IngredientComponent(
    ingredientName : String,
    modifier : Modifier = Modifier,
    selected : Boolean,
    ingredientType : Int = 0,
    onSelect : () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onSelect()
            },
        )
        ImageComponent(
            modifier = modifier
                .size(43.dp)
                .clip(CircleShape),
            imageResource = R.drawable.test_image // ingredientType 값에 맞는 이미지를 로딩해야 합니다.
        )
        TextComponent(
            modifier = Modifier
                .padding( start = 10.dp )
                .weight(1f),
            text = ingredientName,
        )

        IconComponent(
            painter = painterResource(id = R.drawable.icon_delete)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun BodyPreview() {
    IngredientComponent(
        ingredientName = "오이",
        selected = true
    )
}