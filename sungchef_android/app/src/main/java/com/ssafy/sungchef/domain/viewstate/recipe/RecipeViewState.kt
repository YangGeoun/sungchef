package com.ssafy.sungchef.domain.viewstate.recipe

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.ssafy.sungchef.domain.model.ingredient.LackIngredient
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.viewstate.ViewState
import kotlinx.coroutines.flow.Flow

@Stable
data class RecipeViewState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val pagedData: Flow<PagingData<RecipeInfo>>? = null,
): ViewState