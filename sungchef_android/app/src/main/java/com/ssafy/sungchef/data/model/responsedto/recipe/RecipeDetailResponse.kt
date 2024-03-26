package com.ssafy.sungchef.data.model.responsedto.recipe

import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientInfoResponse

data class RecipeDetailResponse(
    val recipeCookingTime: String,
    val recipeDescription: String,
    val recipeDetailList: List<RecipeDetailInfoResponse>,
    val recipeId: Int,
    val recipeImage: String,
    val recipeIngredientInfoList: List<IngredientInfoResponse>,
    val recipeName: String,
    val recipeVolume: String
)