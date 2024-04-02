package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RefrigeratorService {

    @GET("search/ingredient/{ingredientname}")
    suspend fun searchIngredient(
        @Path("ingredientname") ingredientName : String
    ) : Response<ResponseDto<SearchIngredientResponse>>
    @GET("fridge")
    suspend fun getFridgeIngredientList() : Response<ResponseDto<FridgeData>>
    @DELETE("fridge")
    suspend fun deleteFridgeIngredientList() : Response<APIError>
    ) : Response<ResponseDto<List<SearchIngredientResponse>>>

    @POST("fridge")
    suspend fun registerIngredient(
        @Body ingredientRequestDTO: IngredientRequestDTO
    ) : Response<APIError>
}