package com.ssafy.sungchef.domain.viewstate.recipe

import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail

data class RecipeDetailViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isDialog:Boolean = false,
    val dialogTitle: String = "",
    val recipeDetail: RecipeDetail? = null,
    val lackIngredient: LackIngredient? = null
)