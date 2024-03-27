package com.ssafy.sungchef.domain.model.recipe

data class RecipeStep(
    val recipeId: Int,
    val recipeDetailInfoList: List<RecipeDetailInfo>
)
