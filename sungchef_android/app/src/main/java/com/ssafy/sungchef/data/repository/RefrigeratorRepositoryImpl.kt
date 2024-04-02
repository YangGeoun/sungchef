package com.ssafy.sungchef.data.repository

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.datasource.refrigerator.RefrigeratorDataSource
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.ingredient.search.SearchIngredientResponse
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefrigeratorRepositoryImpl @Inject constructor(
    private val refrigeratorDataSource: RefrigeratorDataSource
) : RefrigeratorRepository {
    override suspend fun searchIngredient(ingredientName: String): Flow<DataState<ResponseDto<SearchIngredientResponse>>> {
        return flow {
            val ingredientResponse = refrigeratorDataSource.searchIngredient(ingredientName)

            if (ingredientResponse is DataState.Success) {

            }
        }
    }

    override suspend fun getFridgeIngredientList(): Flow<DataState<ResponseDto<FridgeData>>> {
        Log.d("TAG", "Repo flow전 getFridgeIngredientList: ")

        return flow{
            Log.d("TAG", "Repo getFridgeIngredientList: ")
            emit(refrigeratorDataSource.getFridgeIngredientList())
        }
    }
    override suspend fun deleteFridgeIngredientList(): Flow<DataState<APIError>> {
        return flow {
            refrigeratorDataSource.deleteFridgeIngredientList()
        }

    }

}