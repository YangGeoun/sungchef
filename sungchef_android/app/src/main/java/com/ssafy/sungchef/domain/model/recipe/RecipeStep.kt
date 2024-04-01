package com.ssafy.sungchef.domain.model.recipe

data class RecipeStep(
    val recipeId: Int,
    val recipeName: String,
    val recipeDetailInfoList: List<RecipeDetailInfo>
)
