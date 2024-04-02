package com.ssafy.sungchef.data.mapper.ingredient

import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.domain.model.ingredient.IngredientId
import com.ssafy.sungchef.domain.model.ingredient.IngredientList

fun IngredientList.toIngredientRequestDTO():IngredientRequestDTO{
    return IngredientRequestDTO(
        this.ingredientList.map {
            it.toIngredientId()
        }.toMutableList()
    )
}

fun IngredientId.toIngredientId(): com.ssafy.sungchef.data.model.requestdto.IngredientId{
    return com.ssafy.sungchef.data.model.requestdto.IngredientId(
        this.ingredientId
    )
}