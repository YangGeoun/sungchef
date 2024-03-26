package com.ssafy.sungchef.domain.model.recipe

import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo

data class RecipeDetail(
    val recipeCookingTime: String,
    val recipeDescription: String,
    val recipeDetailInfoList: List<RecipeDetailInfo>,
    val recipeId: Int,
    val recipeImage: String,
    val recipeIngredientInfoList: List<IngredientInfo>,
    val recipeName: String,
    val recipeVolume: String
)