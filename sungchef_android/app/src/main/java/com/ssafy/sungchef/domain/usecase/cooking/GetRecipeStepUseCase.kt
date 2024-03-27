package com.ssafy.sungchef.domain.usecase.cooking

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recipe.RecipeStep
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeStepUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(id:Int): Flow<DataState<RecipeStep>> =
        recipeRepository.getRecipeStep(id)
}