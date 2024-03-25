package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.recipe.RecipeDataSource
import com.ssafy.sungchef.data.mapper.recipe.toRecipeDetail
import com.ssafy.sungchef.data.mapper.recipe.toRecipeInfo
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataSource: RecipeDataSource
) : RecipeRepository {
    override suspend fun getVisitRecipe(page: Int): Flow<DataState<List<RecipeInfo>>> =
        flow {
            val recipeData = recipeDataSource.getVisitRecipe(page)
            if (recipeData is DataState.Success) {
                emit(
                    DataState.Success(
                        recipeData.data.data.recipeList.map {
                            it.toRecipeInfo()
                        }
                    )
                )
            }
        }

    override suspend fun getBookMarkRecipe(page: Int): Flow<DataState<List<RecipeInfo>>> =
        flow {
            val recipeData = recipeDataSource.getBookMarkRecipe(page)
            if (recipeData is DataState.Success) {
                emit(
                    DataState.Success(
                        recipeData.data.data.recipeList.map {
                            it.toRecipeInfo()
                        }
                    )
                )
            }
        }

    override suspend fun getDetailRecipe(id: Int): Flow<DataState<RecipeDetail>> =
        flow {
            val detailRecipe = recipeDataSource.getDetailRecipe(id)
            if (detailRecipe is DataState.Success) {
                emit(
                    DataState.Success(detailRecipe.data.data.toRecipeDetail())
                )
            }
        }

}