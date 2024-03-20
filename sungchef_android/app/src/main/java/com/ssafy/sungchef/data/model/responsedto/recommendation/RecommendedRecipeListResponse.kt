package com.ssafy.sungchef.data.model.responsedto.recommendation

data class RecommendedRecipeListResponse(
    val recipeList: List<RecommendedRecipeResponse>,
    val recommendRecipeType: String
)