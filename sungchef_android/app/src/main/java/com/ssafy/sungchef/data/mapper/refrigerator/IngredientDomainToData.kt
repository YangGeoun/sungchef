package com.ssafy.sungchef.data.mapper.refrigerator

import com.ssafy.sungchef.data.model.requestdto.FoodId
import com.ssafy.sungchef.data.model.requestdto.IngredientId
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO

fun List<Int>.toIngredientRequestDTO() : IngredientRequestDTO {
    val ingredientIdList = this.map{
        IngredientId(it)
    }.toMutableList()

    return IngredientRequestDTO(
        ingredientIdList
    )
}