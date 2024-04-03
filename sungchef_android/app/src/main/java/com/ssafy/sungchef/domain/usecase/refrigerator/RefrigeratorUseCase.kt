package com.ssafy.sungchef.domain.usecase.refrigerator

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.FridgeData
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.domain.model.ingredient.IngredientList
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import com.ssafy.sungchef.features.screen.refrigerator.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefrigeratorUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {

    suspend fun getFridgeIngredientList(): Flow<DataState<ResponseDto<FridgeData>>> {
        Log.d(TAG, "RefrigeratorUseCase 시작 ")
        return refrigeratorRepository.getFridgeIngredientList()
    }
    suspend fun deleteFridgeIngredientList(ingredientList : IngredientList): Flow<DataState<APIError>> {
        return flow{
            refrigeratorRepository.deleteIngredient(ingredientList)
        }
    }

}