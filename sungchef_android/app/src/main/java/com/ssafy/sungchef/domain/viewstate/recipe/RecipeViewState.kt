package com.ssafy.sungchef.domain.viewstate.recipe

import androidx.paging.PagingData
import com.ssafy.sungchef.domain.model.recipe.RecipeDetail
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import kotlinx.coroutines.flow.Flow

data class RecipeViewState (
    val isLoading: Boolean = false,
    val isError:Boolean = false,
    val pagedData: Flow<PagingData<RecipeInfo>>? = null,
    val recipeInfoList: List<RecipeInfo>? = null,
    val recipeDetail: RecipeDetail? = null
)