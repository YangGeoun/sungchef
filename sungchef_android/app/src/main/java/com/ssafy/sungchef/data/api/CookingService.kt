package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.RegisterCookingDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.IngredientListResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeStepResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface CookingService {
    @GET("ingredient/need/{recipeid}")
    suspend fun getLackIngredient(@Path("recipeid") id: Int): Response<ResponseDto<IngredientListResponse>>

    @GET("ingredient/{recipeId}")
    suspend fun getUsedIngredient(@Path("recipeId") id: Int): Response<ResponseDto<IngredientListResponse>>

    @GET("recipe/detail/{recipeid}")
    suspend fun getRecipeStep(@Path("recipeid") id: Int): Response<ResponseDto<RecipeStepResponse>>

    @POST("recipe/makerecipe")
    @Multipart
    suspend fun registerCooking(@Part makeRecipeImage: MultipartBody.Part?, @PartMap params:Map<String, @JvmSuppressWildcards RequestBody>): Response<APIError>
}