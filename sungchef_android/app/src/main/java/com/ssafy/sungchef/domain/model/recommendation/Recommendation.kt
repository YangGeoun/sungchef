package com.ssafy.sungchef.domain.model.recommendation

data class Recommendation(
    val recommendedFoodList: List<RecommendedFoodList>,
    val recommendedRecipeList: List<RecommendedRecipeList>
)