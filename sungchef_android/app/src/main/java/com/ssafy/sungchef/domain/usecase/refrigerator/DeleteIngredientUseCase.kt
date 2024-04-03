package com.ssafy.sungchef.domain.usecase.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.ingredient.IngredientList
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteIngredientUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {
    suspend operator fun invoke(ingredientList: IngredientList): Flow<DataState<BaseModel>> =
        refrigeratorRepository.deleteIngredient(ingredientList)
}