package com.ssafy.sungchef.data.model.responsedto.ingredient

data class IngredientInfoResponse(
    val recipeIngredientList: List<IngredientResponse>,
    val recipeIngredientType: String
)