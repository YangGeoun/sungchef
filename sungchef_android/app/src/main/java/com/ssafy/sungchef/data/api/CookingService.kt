package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CookingService {
    @GET("ingredient/need/{recipeid}")
    suspend fun getLackIngredient(@Path("recipeid") id: Int): Response<ResponseDto<IngredientListResponse>>

    @GET("ingredient/{recipeid}")
    suspend fun getUsedIngredient(@Path("recipeid") id: Int): Response<ResponseDto<IngredientListResponse>>
}