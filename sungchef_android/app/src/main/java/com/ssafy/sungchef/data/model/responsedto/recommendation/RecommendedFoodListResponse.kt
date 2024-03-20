package com.ssafy.sungchef.data.model.responsedto.recommendation

data class RecommendedFoodListResponse(
    val foodList: List<RecommendedFoodResponse>,
    val recommendFoodType: String
)