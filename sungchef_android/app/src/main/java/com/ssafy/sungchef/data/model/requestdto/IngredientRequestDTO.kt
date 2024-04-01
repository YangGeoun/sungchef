package com.ssafy.sungchef.data.model.requestdto

data class IngredientRequestDTO(
    val ingredientList : MutableList<IngredientId>
)

data class IngredientId(
    val ingredientId: Int
)