package com.ssafy.sungchef.data.model.responsedto

data class RecommendedFoodListResponse(
    val foodList: List<RecommendedFoodResponse>,
    val recommendFoodType: String
)