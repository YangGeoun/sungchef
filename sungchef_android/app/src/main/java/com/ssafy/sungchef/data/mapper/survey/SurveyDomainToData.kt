package com.ssafy.sungchef.data.mapper.survey

import com.ssafy.sungchef.data.model.requestdto.FoodId
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO

fun List<Int>.toSurveyRequestDto() : SurveyRequestDTO {
    val foodIdList = this.map{
        FoodId(it)
    }.toMutableList()

    return SurveyRequestDTO(
        foodIdList
    )
}