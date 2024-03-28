package com.ssafy.sungchef.domain.repository

import androidx.paging.PagingData
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.food.FoodName
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.model.recipe.RecipeStep
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    suspend fun getDetailRecipe(id: Int): Flow<DataState<RecipeDetail>>
    suspend fun getAllVisitRecipe(page: Int): Flow<PagingData<RecipeInfo>>
    suspend fun getSearchedVisitRecipe(page: Int, foodName:String): Flow<PagingData<RecipeInfo>>
    suspend fun getSearchedBookmarkRecipe(page: Int, foodName:String): Flow<PagingData<RecipeInfo>>
    suspend fun getAllBookmarkRecipe(page: Int): Flow<PagingData<RecipeInfo>>
    suspend fun getRecipeStep(id:Int):Flow<DataState<RecipeStep>>
    suspend fun searchFoodName(foodName: String):Flow<DataState<List<FoodName>>>
}