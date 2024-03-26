package com.ssafy.sungchef.data.datasource.cooking

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.CookingService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import javax.inject.Inject

class CookingDataSourceImpl @Inject constructor(
    private val cookingService: CookingService
) : CookingDataSource, BaseRemoteDataSource() {
    override suspend fun getLackIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>> =
        getResult { cookingService.getLackIngredient(id) }

    override suspend fun getUsedIngredient(id: Int): DataState<ResponseDto<IngredientListResponse>> =
        getResult { cookingService.getUsedIngredient(id) }
}