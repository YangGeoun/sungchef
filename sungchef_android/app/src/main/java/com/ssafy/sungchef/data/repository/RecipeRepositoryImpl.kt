package com.ssafy.sungchef.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.api.RecipeService
import com.ssafy.sungchef.data.datasource.cooking.CookingDataSource
import com.ssafy.sungchef.data.datasource.recipe.PagingRecipeDataSource
import com.ssafy.sungchef.data.datasource.recipe.RecipeDataSource
import com.ssafy.sungchef.data.datasource.user.BookmarkDataSource
import com.ssafy.sungchef.data.mapper.food.toFoodName
import com.ssafy.sungchef.data.mapper.recipe.toRecipeDetail
import com.ssafy.sungchef.data.mapper.recipe.toRecipeStep
import com.ssafy.sungchef.domain.model.food.FoodName
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.model.recipe.RecipeStep
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataSource: RecipeDataSource,
    private val recipeService: RecipeService,
    private val cookingDataSource: CookingDataSource,
    private val bookmarkDataSource: BookmarkDataSource
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
            pagingSourceFactory = {
                PagingRecipeDataSource(
                    recipeService,
                    bookmarkDataSource = bookmarkDataSource,
                    isVisit = true
                )
            }
        ).flow

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
                    bookmarkDataSource = bookmarkDataSource,
                    name = foodName
                )
            }
        ).flow

    override suspend fun getAllBookmarkRecipe(
        page: Int
    ): Flow<PagingData<RecipeInfo>> =
        Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {
                PagingRecipeDataSource(
                    recipeService,
                    bookmarkDataSource = bookmarkDataSource,
                    isVisit = false
                )
            }
        ).flow

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
                    bookmarkDataSource = bookmarkDataSource,
                    name = foodName
                )
            }
        ).flow

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
                            response.data.data.map {
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