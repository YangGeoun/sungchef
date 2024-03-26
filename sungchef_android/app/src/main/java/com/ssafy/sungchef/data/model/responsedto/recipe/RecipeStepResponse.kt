package com.ssafy.sungchef.data.model.responsedto.recipe

data class RecipeStepResponse(
    val recipeId:Int,
    val recipeDetailList: List<RecipeDetailInfoResponse>
)
