package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.refrigerator.RefrigeratorDataSource
import com.ssafy.sungchef.data.mapper.refrigerator.toIngredientRequestDTO
import com.ssafy.sungchef.data.mapper.refrigerator.toSearchIngredient
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.IngredientId
import com.ssafy.sungchef.data.model.requestdto.IngredientRequestDTO
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefrigeratorRepositoryImpl @Inject constructor(
    private val refrigeratorDataSource: RefrigeratorDataSource
) : RefrigeratorRepository {
    override suspend fun searchIngredient(ingredientName: String): Flow<DataState<List<SearchIngredient>>> {
        return flow {
            val ingredientResponse = refrigeratorDataSource.searchIngredient(ingredientName)

            if (ingredientResponse is DataState.Success) {
                emit(DataState.Success(ingredientResponse.data.data.toSearchIngredient()))
            } else if (ingredientResponse is DataState.Error) {
                emit(DataState.Error(ingredientResponse.apiError))
            }
        }
    }

    override suspend fun registerIngredient(ingredientIdList: List<Int>): Flow<DataState<APIError>> {
        return flow {
            val ingredientRequestDTO = ingredientIdList.toIngredientRequestDTO()

            val registerResponse = refrigeratorDataSource.registerIngredient(ingredientRequestDTO)

            if (registerResponse is DataState.Success) {
                emit(DataState.Success(registerResponse.data))
            } else if (registerResponse is DataState.Error) {
                emit(DataState.Error(registerResponse.apiError))
            }
        }
    }

}