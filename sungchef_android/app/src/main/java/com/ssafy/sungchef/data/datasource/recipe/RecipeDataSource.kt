package com.ssafy.sungchef.data.datasource.recipe

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.food.FoodListResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailResponse

interface RecipeDataSource {
    suspend fun getDetailRecipe(id:Int): DataState<ResponseDto<RecipeDetailResponse>>

    suspend fun searchFoodName(foodName: String): DataState<ResponseDto<FoodListResponse>>
}