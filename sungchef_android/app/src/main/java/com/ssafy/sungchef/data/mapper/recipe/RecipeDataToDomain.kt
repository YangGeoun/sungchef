package com.ssafy.sungchef.data.mapper.recipe

import com.ssafy.sungchef.data.mapper.ingredient.toRecipeIngredientInfo
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailInfoResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeInfoResponse
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeDetailInfo
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

fun RecipeDetailResponse.toRecipeDetail(): RecipeDetail {
    return RecipeDetail(
        this.recipeCookingTime,
        this.recipeDescription,
        this.recipeDetailList.map {
            it.toRecipeDetailInfo()
        },
        this.recipeId,
        this.recipeImage,
        this.recipeIngredientInfoList.map {
            it.toRecipeIngredientInfo()
        },
        this.recipeName,
        this.recipeVolume,
    )
}

fun RecipeDetailInfoResponse.toRecipeDetailInfo(): RecipeDetailInfo {
    return RecipeDetailInfo(
        this.recipeDetailDescription,
        this.recipeDetailImage,
        this.recipeDetailStep
    )
}