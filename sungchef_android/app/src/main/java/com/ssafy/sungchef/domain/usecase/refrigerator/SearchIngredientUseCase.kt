package com.ssafy.sungchef.domain.usecase.refrigerator

import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import javax.inject.Inject

class SearchIngredientUseCase @Inject constructor(
    private val refrigeratorRepository: RefrigeratorRepository
) {
}