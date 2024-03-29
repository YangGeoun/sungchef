package com.ssafy.sungchef.data.mapper.ingredient

import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientInfoResponse
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientResponse
import com.ssafy.sungchef.domain.model.ingredient.Ingredient
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient

fun IngredientListResponse.toLackIngredient(): LackIngredient {
    return LackIngredient(
        this.recipeId,
        this.recipeIngredientInfoList.map {
            it.toRecipeIngredientInfo()
        }
    )
}

fun IngredientInfoResponse.toRecipeIngredientInfo(): IngredientInfo {
    val map: Map<String, String> = mapOf(
        "FRUIT" to "과일",
        "VEGETABLE" to "채소",
        "RICE_GRAIN" to "쌀/잡곡",
        "MEAT_EGG" to "육류",
        "FISH" to "수산",
        "MILK" to "유제품",
        "SAUCE" to "소스/양념",
        "ETC" to "기타"
    )
    return IngredientInfo(
        this.recipeIngredientList.map {
            it.toRecipeIngredient()
        },
        map.get(this.recipeIngredientType)!!
    )
}

fun IngredientResponse.toRecipeIngredient(): Ingredient {
    return Ingredient(
        this.recipeIngredientId,
        this.recipeIngredientName,
        this.recipeIngredientVolume
    )
}