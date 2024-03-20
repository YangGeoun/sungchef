package com.ssafy.sungchef.data.model.responsedto

data class RecommendedRecipeListResponse(
    val recipeList: List<RecommendedRecipeResponse>,
    val recommendRecipeType: String
)