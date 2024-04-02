package com.ssafy.sungchef.domain.usecase.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterIngredientUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {

    suspend fun registerIngredient(ingredientIdList : List<Int>) : Flow<DataState<APIError>> {
        return refrigeratorRepository.registerIngredient(ingredientIdList)
    }
}