package com.ssafy.sungchef.domain.usecase.recipe

import androidx.paging.PagingData
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchedBookmarkUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(page: Int, name: String): Flow<PagingData<RecipeInfo>> {
        return recipeRepository.getSearchedBookmarkRecipe(page, name)
    }
}