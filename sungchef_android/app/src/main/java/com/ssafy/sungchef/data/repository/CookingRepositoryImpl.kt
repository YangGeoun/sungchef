package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.cooking.CookingDataSource
import com.ssafy.sungchef.data.mapper.ingredient.toLackIngredient
import com.ssafy.sungchef.data.mapper.ingredient.toRecipeIngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.domain.repository.CookingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CookingRepositoryImpl @Inject constructor(
    private val cookingDataSource: CookingDataSource
) : CookingRepository {
    override suspend fun getLackIngredient(id: Int): Flow<DataState<LackIngredient>> =
        flow {
            when (val ingredient = cookingDataSource.getLackIngredient(id)) {
                is DataState.Success -> {
                    emit(DataState.Success(ingredient.data.data.toLackIngredient()))
                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }

                is DataState.Error -> {

                }
            }
        }.onStart { emit(DataState.Loading()) }

    override suspend fun getUsedIngredient(id: Int): Flow<DataState<LackIngredient>> =
        flow {
            when (val ingredient = cookingDataSource.getUsedIngredient(id)) {
                is DataState.Success -> {
                    emit(DataState.Success(ingredient.data.data.toLackIngredient()))
                }

                is DataState.Loading -> {
                    emit(DataState.Loading())
                }

                is DataState.Error -> {

                }
            }
        }.onStart { emit(DataState.Loading()) }
}