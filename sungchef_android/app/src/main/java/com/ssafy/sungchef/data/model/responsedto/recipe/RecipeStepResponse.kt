package com.ssafy.sungchef.data.model.responsedto.recipe

data class RecipeStepResponse(
    val recipeId:Int,
    val recipeName: String,
    val recipeDetailList: List<RecipeDetailInfoResponse>
)
