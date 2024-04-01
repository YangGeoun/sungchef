package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ChangeBookmarkRecipeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(recipeId: Int, isBookmark: Boolean): Flow<DataState<BaseModel>> =
        userRepository.changeBookmarkRecipe(recipeId, isBookmark)
}