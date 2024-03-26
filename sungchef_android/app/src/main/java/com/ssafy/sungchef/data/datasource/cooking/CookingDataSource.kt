package com.ssafy.sungchef.data.datasource.cooking

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse

interface CookingDataSource {
    suspend fun getLackIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>>
    suspend fun getUsedIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>>
}