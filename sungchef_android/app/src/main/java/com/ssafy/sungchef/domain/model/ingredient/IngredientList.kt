package com.ssafy.sungchef.domain.model.ingredient

data class IngredientList(
    val ingredientList: List<IngredientId> = mutableListOf()
)

data class IngredientId(
    val ingredientId: Int
)
