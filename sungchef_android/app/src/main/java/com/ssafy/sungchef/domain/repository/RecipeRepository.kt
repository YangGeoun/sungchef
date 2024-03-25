package com.ssafy.sungchef.domain.repository

import androidx.paging.PagingData
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    suspend fun getDetailRecipe(id: Int): Flow<DataState<RecipeDetail>>

    suspend fun getAllVisitRecipe(page: Int, isVisit: Boolean): Flow<PagingData<RecipeInfo>>
    suspend fun getAllBookmarkRecipe(page: Int, isVisit: Boolean): Flow<PagingData<RecipeInfo>>
}