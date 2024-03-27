package com.ssafy.sungchef.domain.viewstate.cooking

import androidx.compose.runtime.Stable
import com.ssafy.sungchef.domain.model.recipe.RecipeDetailInfo
import com.ssafy.sungchef.domain.viewstate.ViewState

@Stable
data class CookingViewState(
    val isLoading: Boolean = false,
    val count: Int = 0,
    val recipeDetailList: List<RecipeDetailInfo> = listOf()
) : ViewState
