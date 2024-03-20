package com.ssafy.sungchef.domain.model.recommendation

data class Recommendation(
    val recommendedFoodListList: List<RecommendedFoodList>,
    val recommendedRecipeListList: List<RecommendedRecipeList>
)