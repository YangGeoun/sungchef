package com.ssafy.sungchef.domain.model.recipe

data class RecipeDetail(
    val recipeCookingTime: String,
    val recipeDescription: String,
    val recipeDetailInfoList: List<RecipeDetailInfo>,
    val recipeId: Int,
    val recipeImage: String,
    val recipeIngredientInfoList: List<RecipeIngredientInfo>,
    val recipeName: String,
    val recipeVolume: String
)