package com.ssafy.sungchef.data.mapper.ingredient

import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientInfoResponse
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientResponse
import com.ssafy.sungchef.domain.model.ingredient.Ingredient
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient

fun IngredientListResponse.toLackIngredient(): LackIngredient{
    return LackIngredient(
        this.recipeId,
        this.recipeIngredientInfoList.map {
            it.toRecipeIngredientInfo()
        }
    )
}

fun IngredientInfoResponse.toRecipeIngredientInfo(): IngredientInfo {
    return IngredientInfo(
        this.recipeIngredientList.map {
            it.toRecipeIngredient()
        },
        this.recipeIngredientType
    )
}

fun IngredientResponse.toRecipeIngredient(): Ingredient {
    return Ingredient(
        this.recipeIngredientId,
        this.recipeIngredientName,
        this.recipeIngredientVolume
    )
}