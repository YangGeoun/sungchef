package com.ssafy.sungchef.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.datasource.cooking.CookingDataSource
import com.ssafy.sungchef.data.datasource.recipe.PagingRecipeDataSource
import com.ssafy.sungchef.data.datasource.recipe.RecipeDataSource
import com.ssafy.sungchef.data.mapper.food.toFoodName
import com.ssafy.sungchef.data.mapper.recipe.toRecipeDetail
import com.ssafy.sungchef.data.mapper.recipe.toRecipeDetailInfo
import com.ssafy.sungchef.data.mapper.recipe.toRecipeInfo
import com.ssafy.sungchef.data.mapper.recipe.toRecipeStep
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.food.FoodName
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.model.recipe.RecipeStep
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataSource: RecipeDataSource,
    private val recipeService: RecipeService,
    private val cookingDataSource: CookingDataSource
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
        page: Int
    ): Flow<PagingData<RecipeInfo>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { PagingRecipeDataSource(recipeService, isVisit = true) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toRecipeInfo()
            }
        }

    override suspend fun getSearchedVisitRecipe(
        page: Int,
        foodName: String
    ): Flow<PagingData<RecipeInfo>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                PagingRecipeDataSource(
                    recipeService,
                    isVisit = true,
                    name = foodName
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toRecipeInfo()
            }
        }

    override suspend fun getAllBookmarkRecipe(
        page: Int
    ): Flow<PagingData<RecipeInfo>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { PagingRecipeDataSource(recipeService, isVisit = false) }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toRecipeInfo()
            }
        }

    override suspend fun getSearchedBookmarkRecipe(
        page: Int,
        foodName: String
    ): Flow<PagingData<RecipeInfo>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                PagingRecipeDataSource(
                    recipeService,
                    isVisit = false,
                    name = foodName
                )
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toRecipeInfo()
            }
        }

    override suspend fun getRecipeStep(id: Int): Flow<DataState<RecipeStep>> =
        flow {
            when (val recipeStep = cookingDataSource.getRecipeStep(id)) {
                is DataState.Success -> {
                    emit(DataState.Success(recipeStep.data.data.toRecipeStep()))
                }

                is DataState.Loading -> {
                    emit(
                        DataState.Loading()
                    )
                }

                is DataState.Error -> {
                    emit(DataState.Error(recipeStep.apiError))
                }
            }
        }

    override suspend fun searchFoodName(foodName: String): Flow<DataState<List<FoodName>>> =
        flow<DataState<List<FoodName>>> {
            when (val response = recipeDataSource.searchFoodName(foodName)) {
                is DataState.Success -> {
                    emit(
                        DataState.Success(
                            response.data.data.foodList.map {
                                it.toFoodName()
                            }
                        )
                    )
                }

                is DataState.Loading -> {

                }

                is DataState.Error -> {

                }
            }
        }.onStart { emit(DataState.Loading()) }

}