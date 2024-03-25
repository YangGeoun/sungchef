package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    suspend fun getVisitRecipe(page: Int): Flow<DataState<List<RecipeInfo>>>
    suspend fun getBookMarkRecipe(page: Int): Flow<DataState<List<RecipeInfo>>>
    suspend fun getDetailRecipe(id:Int):Flow<DataState<RecipeDetail>>
}