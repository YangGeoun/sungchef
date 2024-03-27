package com.ssafy.sungchef.features.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.sungchef.domain.model.recipe.RecipeDetailInfo
import com.ssafy.sungchef.features.screen.menu.RecipeImageComponent

@Composable
fun RecipeCardComponent(
    modifier: Modifier = Modifier,
    size: Int = 1,
    recipeDetailInfo: RecipeDetailInfo
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 5.dp),
    ) {
        RecipeImageComponent(
            modifier = modifier
                .size((120 * size).dp)
                .clip(RoundedCornerShape(15.dp)),
            recipeDetailInfo.recipeDetailImage
        )
        Spacer(modifier = modifier.size(30.dp))
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                TextComponent(
                    text = "Step${recipeDetailInfo.recipeDetailStep}",
                    modifier = modifier.padding(horizontal = 10.dp)
                )
            }
            TextComponent(
                text = recipeDetailInfo.recipeDetailDescription,
                modifier = modifier.padding(top = 10.dp)
            )
        }
    }
}