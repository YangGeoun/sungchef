package com.ssafy.sungchef.domain.viewstate.cooking

import androidx.compose.runtime.Stable
import com.ssafy.sungchef.domain.model.ingredient.Ingredient
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.domain.model.recipe.RecipeDetailInfo
import com.ssafy.sungchef.domain.viewstate.ViewState

@Stable
data class CookingViewState(
    val isLoading: Boolean = false,
    val count: Int = 0,
    val recipeName: String = "",
    val recipeDetailList: List<RecipeDetailInfo> = listOf(),
    val usedIngredient: LackIngredient? = null,
    val usingIngredient: List<Ingredient> = listOf()
) : ViewState
