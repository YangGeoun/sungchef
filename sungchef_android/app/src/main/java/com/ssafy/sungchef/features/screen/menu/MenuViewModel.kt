package com.ssafy.sungchef.features.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.usecase.recipe.GetBookMarkRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetDetailRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetVisitRecipeUseCase
import com.ssafy.sungchef.domain.usecase.user.ChangeBookmarkRecipe
import com.ssafy.sungchef.domain.viewstate.recipe.RecipeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getVisitRecipeUseCase: GetVisitRecipeUseCase,
    private val getBookMarkRecipeUseCase: GetBookMarkRecipeUseCase,
    private val getDetailRecipeUseCase: GetDetailRecipeUseCase,
    private val changeBookmarkRecipe: ChangeBookmarkRecipe
) : ViewModel() {
    private val initialState: RecipeViewState by lazy { RecipeViewState() }
    private val currentState: RecipeViewState get() = uiState.value
    private val _uiState: MutableStateFlow<RecipeViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<RecipeViewState> = _uiState

    fun getVisitRecipeInfo(page: Int) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val pagedFlow = getVisitRecipeUseCase(page, true).cachedIn(viewModelScope)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }

    init {
        getVisitRecipeInfo(0)
    }

    fun getBookMarkRecipeInfo(page: Int) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val pagedFlow = getBookMarkRecipeUseCase(page, false).cachedIn(viewModelScope)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }

    fun getDetailRecipe(id: Int) {
        viewModelScope.launch {
            getDetailRecipeUseCase(id).collect {
                when (it) {
                    is DataState.Success -> {
                        setState { currentState.copy(isLoading = false, recipeDetail = it.data) }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isLoading = false) }
                        when (it.apiError.code) {
                            400.toLong() -> setState { currentState.copy(isError = true) }
                        }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun resetDetailRecipe() {
        setState { currentState.copy(recipeDetail = null) }
    }

    fun resetError() {
        setState { currentState.copy(isError = false) }
    }

    fun changeBookmarkRecipe(recipeId: Int, isBookmark: Boolean) {
        viewModelScope.launch {
            changeBookmarkRecipe.invoke(recipeId, isBookmark).collect() {
                when (it) {
                    is DataState.Success -> {
                        setState { currentState.copy(isLoading = false) }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isError = true, isLoading = false) }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isError = true, isLoading = true) }
                    }
                }
            }
        }
    }

    private fun setState(reduce: RecipeViewState.() -> RecipeViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }
}