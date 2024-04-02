package com.ssafy.sungchef.data.datasource.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import retrofit2.Response

interface RefrigeratorDataSource {

    suspend fun getFridgeIngredientList() : DataState<ResponseDto<FridgeData>>
    suspend fun deleteFridgeIngredientList() : DataState<APIError>

    suspend fun searchIngredient(ingredientName : String) : DataState<ResponseDto<List<SearchIngredientResponse>>>

    suspend fun registerIngredient(ingredientRequestDTO: IngredientRequestDTO) : DataState<APIError>
}