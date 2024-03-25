package com.ssafy.sungchef.data.model.requestdto

data class SurveyRequestDTO(
    val foodIdList : MutableList<FoodId>
)

data class FoodId(
    val foodId : Int
)
