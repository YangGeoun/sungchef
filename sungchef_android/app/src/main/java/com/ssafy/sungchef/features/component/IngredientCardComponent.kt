package com.ssafy.sungchef.features.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.sungchef.R
import com.ssafy.sungchef.domain.model.ingredient.Ingredient

@Composable
fun IngredientCardComponent(
    modifier: Modifier = Modifier,
    classification: String = "",
    @DrawableRes id:Int,
    recipeIngredients: List<Ingredient>,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            modifier = modifier
                .size(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = id),
            contentDescription = "사진"
        )
        Spacer(modifier = modifier.size(2.dp))
        //반복문 돌리기
        Column {
            TextComponent(
                modifier = modifier
                    .height(36.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                text = classification,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            recipeIngredients.map {
                Row(
                    modifier = modifier.padding(top = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = modifier.size(2.dp))
                    TextComponent(
                        text = it.recipeIngredientName,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = modifier.size(5.dp))
                    TextComponent(
                        text = it.recipeIngredientVolume,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}