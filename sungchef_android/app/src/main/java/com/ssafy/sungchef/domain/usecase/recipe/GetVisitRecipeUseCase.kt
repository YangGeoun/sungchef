package com.ssafy.sungchef.domain.usecase.recipe

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVisitRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(page: Int): Flow<DataState<List<RecipeInfo>>> {
        return recipeRepository.getVisitRecipe(page)
    }
}