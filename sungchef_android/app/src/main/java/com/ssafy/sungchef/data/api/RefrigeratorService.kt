package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RefrigeratorService {

    @GET("search/ingredient/{ingredientname}")
    suspend fun searchIngredient(
        @Path("ingredientname") ingredientName : String
    ) : Response<ResponseDto<List<SearchIngredientResponse>>>
}