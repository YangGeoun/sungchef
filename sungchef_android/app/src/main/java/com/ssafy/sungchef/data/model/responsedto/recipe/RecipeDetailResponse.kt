package com.ssafy.sungchef.data.model.responsedto.recipe

data class RecipeDetailResponse(
    val recipeCookingTime: String,
    val recipeDescription: String,
    val recipeDetailList: List<RecipeDetailInfoResponse>,
    val recipeId: Int,
    val recipeImage: String,
    val recipeIngredientInfoList: List<RecipeIngredientInfoResponse>,
    val recipeName: String,
    val recipeVolume: String
)