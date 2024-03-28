package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.food.FoodListResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.SearchedRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("recipe/{recipeId}")
    suspend fun getDetailRecipe(@Path("recipeId") id: String): Response<ResponseDto<RecipeDetailResponse>>

    @GET("recipe/visit/{page}")
    suspend fun getAllVisitRecipe(@Path("page") page: Int): ResponseDto<SearchedRecipeResponse>

    @GET("recipe/search/visit/{foodName}/{page}")
    suspend fun getSearchVisitRecipe(
        @Path("foodName") foodName: String,
        @Path("page") page: Int
    ): ResponseDto<SearchedRecipeResponse>

    @GET("recipe/bookmark/{page}")
    suspend fun getAllBookMarkRecipe(@Path("page") page: Int): ResponseDto<SearchedRecipeResponse>

    @GET("recipe/search/bookmark/{foodName}/{page}")
    suspend fun getSearchBookmarkRecipe(
        @Path("foodName") foodName: String,
        @Path("page") page: Int
    ): ResponseDto<SearchedRecipeResponse>

    @GET("search/food/{foodname}")
    suspend fun searchFoodName(@Path("foodname") foodName: String): Response<ResponseDto<FoodListResponse>>
}