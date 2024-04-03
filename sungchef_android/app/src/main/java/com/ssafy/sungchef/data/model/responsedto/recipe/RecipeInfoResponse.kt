package com.ssafy.sungchef.data.model.responsedto.recipe

data class RecipeInfoResponse(
    val bookmark: Boolean,
    val recipeCookingTime: String,
    val recipeId: Int,
    val recipeImage: String,
    val recipeName: String,
    val recipeVisitCount: Int,
    val recipeBookmarkCount: Int,
    val recipeVolume: String
)