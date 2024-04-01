package com.ssafy.sungchef.domain.model.ingredient

data class IngredientList(
    val ingredientList: MutableList<IngredientId> = mutableListOf()
)

data class IngredientId(
    val ingredientId: Int
)
