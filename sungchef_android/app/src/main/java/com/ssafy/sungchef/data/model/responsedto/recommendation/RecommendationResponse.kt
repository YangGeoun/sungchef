package com.ssafy.sungchef.data.model.responsedto.recommendation

data class RecommendationResponse(
    val recommendFoodList: List<RecommendedFoodListResponse>,
    val recommendRecipeList: List<RecommendedRecipeListResponse>
)