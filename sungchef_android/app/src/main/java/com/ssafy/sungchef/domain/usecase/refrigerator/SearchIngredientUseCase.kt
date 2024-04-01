package com.ssafy.sungchef.domain.usecase.refrigerator

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchIngredientUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {
    suspend fun searchIngredient(ingredientName : String) : Flow<DataState<List<SearchIngredient>>> {
        return refrigeratorRepository.searchIngredient(ingredientName)
    }
}