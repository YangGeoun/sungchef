package com.ssafy.sungchef.domain.usecase.cooking

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.ingredient.IngredientInfo
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.domain.repository.CookingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsedIngredientUseCase @Inject constructor(
    private val cookingRepository: CookingRepository
) {
    suspend operator fun invoke(id: Int): Flow<DataState<LackIngredient>> =
        cookingRepository.getUsedIngredient(id)
}