package com.ssafy.sungchef.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.datasource.recipe.PagingRecipeDataSource
import com.ssafy.sungchef.data.datasource.recipe.RecipeDataSource
import com.ssafy.sungchef.data.mapper.recipe.toRecipeDetail
import com.ssafy.sungchef.data.mapper.recipe.toRecipeInfo
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataSource: RecipeDataSource,
    private val recipeService: RecipeService
) : RecipeRepository {
    override suspend fun getDetailRecipe(id: Int): Flow<DataState<RecipeDetail>> =
        flow {
            when (val detailRecipe = recipeDataSource.getDetailRecipe(id)) {
                is DataState.Success -> {
                    emit(DataState.Success(detailRecipe.data.data.toRecipeDetail()))
                }

                is DataState.Loading -> {
                    emit(
                        DataState.Loading()
                    )
                }

                is DataState.Error -> {
                    emit(DataState.Error(detailRecipe.apiError))
                }
            }
        }

    override suspend fun getAllVisitRecipe(
        page: Int,
        isVisit: Boolean
    ): Flow<PagingData<RecipeInfo>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { PagingRecipeDataSource(recipeService, isVisit) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toRecipeInfo()
            }
        }

    override suspend fun getAllBookmarkRecipe(
        page: Int,
        isVisit: Boolean
    ): Flow<PagingData<RecipeInfo>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { PagingRecipeDataSource(recipeService, isVisit) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toRecipeInfo()
            }
        }
}