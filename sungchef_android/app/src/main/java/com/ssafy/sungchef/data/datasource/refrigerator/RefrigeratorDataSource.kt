package com.ssafy.sungchef.data.datasource.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.FridgeData

import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.requestdto.RecipeRequest
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import com.ssafy.sungchef.data.model.responsedto.ocr.OcrResponse

import okhttp3.MultipartBody

import retrofit2.Response
import retrofit2.http.Path


interface RefrigeratorDataSource {
    suspend fun getFridgeIngredientList() : DataState<ResponseDto<FridgeData>>
    suspend fun searchIngredient(ingredientName : String) : DataState<ResponseDto<List<SearchIngredientResponse>>>
    suspend fun registerIngredient(ingredientRequestDTO: IngredientRequestDTO) : DataState<APIError>
    suspend fun registerReceipt(convertImage : MultipartBody.Part?) : DataState<ResponseDto<String>>

    suspend fun getOcrConvert(convertOCRKey : String) : DataState<ResponseDto<OcrResponse>>

    suspend fun deleteIngredient(ingredientRequestDTO: IngredientRequestDTO): DataState<APIError>
    suspend fun registerNeedIngredient(recipeRequest: RecipeRequest): DataState<APIError>
}