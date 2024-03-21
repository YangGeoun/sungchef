package com.ssafy.sungchef.data.mapper.recipe

import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeInfoResponse
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo

fun RecipeInfoResponse.toRecipeInfo(): RecipeInfo {
    return RecipeInfo(
        this.bookmark,
        this.recipeCookingTime,
        this.recipeId,
        this.recipeImage,
        this.recipeName,
        this.recipeVisitCount,
        this.recipeVolume
    )
}