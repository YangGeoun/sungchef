package com.ssafy.sungchef.data.mapper.recipe

import com.ssafy.sungchef.data.mapper.ingredient.toLackIngredient
import com.ssafy.sungchef.data.mapper.ingredient.toRecipeIngredientInfo
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailInfoResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeInfoResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeStepResponse
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeDetailInfo
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.model.recipe.RecipeStep

fun RecipeInfoResponse.toRecipeInfo(): RecipeInfo {
    return RecipeInfo(
        this.bookmark,
        this.recipeCookingTime,
        this.recipeId,
        this.recipeImage,
        this.recipeName,
        this.recipeVisitCount,
        this.recipeBookmarkCount,
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
        this.recipeIngredientInfoList.toLackIngredient(),
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

fun RecipeStepResponse.toRecipeStep(): RecipeStep{
    return RecipeStep(
        this.recipeId,
        this.recipeName,
        this.recipeDetailList.map {
            it.toRecipeDetailInfo()
        }
    )
}