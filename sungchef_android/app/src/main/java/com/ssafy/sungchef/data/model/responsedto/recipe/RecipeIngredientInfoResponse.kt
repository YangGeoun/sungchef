package com.ssafy.sungchef.data.model.responsedto.recipe

data class RecipeIngredientInfoResponse(
    val recipeIngredientList: List<RecipeIngredientResponse>,
    val recipeIngredientType: String
)