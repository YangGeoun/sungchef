package com.ssafy.sungchef.domain.usecase.recipe

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetDetailRecipeUseCase @Inject constructor(
    private val detailRecipeRepository: RecipeRepository
) {
    suspend operator fun invoke(id:Int): Flow<DataState<RecipeDetail>>{
        return detailRecipeRepository.getDetailRecipe(id)
            .onStart { emit(DataState.Loading()) }
    }
}