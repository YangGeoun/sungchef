package com.ssafy.sungchef.data.datasource.recipe

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.recipe.RecipeDetailResponse
import com.ssafy.sungchef.data.model.responsedto.recipe.SearchedRecipeResponse
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(
    private val recipeService: RecipeService
) : RecipeDataSource, BaseRemoteDataSource() {

    override suspend fun getVisitRecipe(page: Int): DataState<ResponseDto<SearchedRecipeResponse>> =
        getResult { recipeService.getVisitRecipe(page) }

    override suspend fun getBookMarkRecipe(page: Int): DataState<ResponseDto<SearchedRecipeResponse>> =
        getResult { recipeService.getBookMarkRecipe(page) }

    override suspend fun getDetailRecipe(id: Int): DataState<ResponseDto<RecipeDetailResponse>> =
        getResult { recipeService.getDetailRecipe(id) }
}