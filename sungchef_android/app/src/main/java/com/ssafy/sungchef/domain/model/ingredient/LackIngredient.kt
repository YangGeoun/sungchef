package com.ssafy.sungchef.domain.model.ingredient

data class LackIngredient(
    val recipeId:Int,
    val ingredientInfo: List<IngredientInfo>
)