package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.SearchedRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {

    @GET("recipe/visit/{page}")
    suspend fun getVisitRecipe(@Path("page")page: Int): Response<ResponseDto<SearchedRecipeResponse>>

    @GET("recipe/bookmark/{page}")
    suspend fun getBookMarkRecipe(@Path("page")page: Int): Response<ResponseDto<SearchedRecipeResponse>>

    @GET("recipe/{recipeid}")
    suspend fun getDetailRecipe(@Path("recipeid")id:Int):Response<ResponseDto<RecipeDetailResponse>>
}