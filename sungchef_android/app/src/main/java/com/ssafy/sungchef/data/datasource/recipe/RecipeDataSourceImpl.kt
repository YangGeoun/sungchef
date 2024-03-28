package com.ssafy.sungchef.data.datasource.recipe

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.food.FoodListResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.SearchedRecipeResponse
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(
    private val recipeService: RecipeService
) : RecipeDataSource, BaseRemoteDataSource() {

    override suspend fun getDetailRecipe(id: Int): DataState<ResponseDto<RecipeDetailResponse>> =
        getResult { recipeService.getDetailRecipe(id) }

    override suspend fun searchFoodName(foodName: String): DataState<ResponseDto<FoodListResponse>> =
        getResult { recipeService.searchFoodName(foodName) }
}