package com.ssafy.sungchef.domain.viewstate.recommendation

import androidx.compose.runtime.Stable
import com.ssafy.sungchef.domain.model.recommendation.RecommendedFoodList
import com.ssafy.sungchef.domain.model.recommendation.RecommendedRecipeList
import com.ssafy.sungchef.domain.model.recommendation.UserInfo
import com.ssafy.sungchef.domain.viewstate.ViewState

@Stable
data class RecommendationViewState(
    val isLoading: Boolean = false,
    val recommendFood: List<RecommendedFoodList>? = null,
    val recommendRecipe: List<RecommendedRecipeList>? = null,
    val user: UserInfo? = null
) : ViewState
