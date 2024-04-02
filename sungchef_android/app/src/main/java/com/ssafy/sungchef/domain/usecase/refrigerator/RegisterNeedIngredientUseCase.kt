package com.ssafy.sungchef.domain.usecase.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterNeedIngredientUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {
    suspend operator fun invoke(id: Int): Flow<DataState<BaseModel>> =
        refrigeratorRepository.registerNeedIngredient(id)
}