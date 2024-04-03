package com.ssafy.sungchef.domain.model.recipe

data class RecipeInfo(
    var bookmark: Boolean,
    val recipeCookingTime: String,
    val recipeId: Int,
    val recipeImage: String,
    val recipeName: String,
    val recipeVisitCount: Int,
    val recipeBookmarkCount: Int,
    val recipeVolume: String
)