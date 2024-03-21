package com.ssafy.sungchef.data.datasource.recipe

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.recipe.SearchedRecipeResponse

interface RecipeDataSource {
    suspend fun getVisitRecipe(page: Int): DataState<ResponseDto<SearchedRecipeResponse>>
    suspend fun getBookMarkRecipe(page: Int): DataState<ResponseDto<SearchedRecipeResponse>>
}