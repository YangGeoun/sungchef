package com.ssafy.sungchef.domain.usecase.recipe

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.food.FoodName
import com.ssafy.sungchef.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodNameUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(foodName: String): Flow<DataState<List<FoodName>>> =
        recipeRepository.searchFoodName(foodName)
}