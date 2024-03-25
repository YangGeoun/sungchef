package com.ssafy.sungchef.domain.viewstate.recipe

import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo

data class RecipeViewState (
    val isLoading: Boolean = false,
    val recipeInfoList: List<RecipeInfo>? = null,
    val recipeDetail: RecipeDetail? = null
)