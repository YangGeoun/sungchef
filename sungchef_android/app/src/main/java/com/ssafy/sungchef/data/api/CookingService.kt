package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeStepResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CookingService {
    @GET("ingredient/need/{recipeid}")
    suspend fun getLackIngredient(@Path("recipeid") id: Int): Response<ResponseDto<IngredientListResponse>>

    @GET("ingredient/recipe/{recipeId}")
    suspend fun getUsedIngredient(@Path("recipeId") id: Int): Response<ResponseDto<IngredientListResponse>>

    @GET("recipe/detail/{recipeid}")
    suspend fun getRecipeStep(@Path("recipeid")id:Int):Response<ResponseDto<RecipeStepResponse>>
}