package com.ssafy.sungchef.domain.viewstate.recommendation

import androidx.compose.runtime.Stable
import com.ssafy.sungchef.domain.model.recommendation.RecommendedFoodList
import com.ssafy.sungchef.domain.model.recommendation.RecommendedRecipeList

@Stable
data class RecommendationViewState(
    val isLoading: Boolean = false,
    val recommendFood: List<RecommendedFoodList>?=null,
    val recommendRecipe: List<RecommendedRecipeList>?=null
)
