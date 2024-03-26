package com.ssafy.sungchef.features.screen.cooking

import androidx.lifecycle.ViewModel
import com.ssafy.sungchef.domain.usecase.cooking.GetUsedIngredientUseCase
import javax.inject.Inject

class CookingViewModel @Inject constructor(
    private val getUsedIngredientUseCase: GetUsedIngredientUseCase
): ViewModel() {

}