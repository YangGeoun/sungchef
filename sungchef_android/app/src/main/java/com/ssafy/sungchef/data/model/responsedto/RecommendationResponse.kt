package com.ssafy.sungchef.data.model.responsedto

data class RecommendationResponse(
    val recommendFoodList: List<RecommendedFoodListResponse>,
    val recommendRecipeList: List<RecommendedRecipeListResponse>
)