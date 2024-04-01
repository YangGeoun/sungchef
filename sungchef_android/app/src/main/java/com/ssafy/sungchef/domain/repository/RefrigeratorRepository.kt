package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import kotlinx.coroutines.flow.Flow

interface RefrigeratorRepository {

    suspend fun searchIngredient(ingredientName : String) : Flow<DataState<ResponseDto<SearchIngredientResponse>>>
}