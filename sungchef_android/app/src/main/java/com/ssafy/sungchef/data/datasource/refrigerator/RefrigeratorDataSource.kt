package com.ssafy.sungchef.data.datasource.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse

interface RefrigeratorDataSource {

    suspend fun searchIngredient(ingredientName : String) : DataState<ResponseDto<SearchIngredientResponse>>
}