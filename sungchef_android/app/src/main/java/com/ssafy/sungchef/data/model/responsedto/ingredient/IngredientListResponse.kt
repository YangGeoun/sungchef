package com.ssafy.sungchef.data.model.responsedto.ingredient

data class IngredientListResponse (
    val recipeId: Int,
    val recipeIngredientInfoList:List<IngredientInfoResponse>
)