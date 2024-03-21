package com.ssafy.sungchef.features.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.usecase.GetVisitRecipeUseCase
import com.ssafy.sungchef.domain.viewstate.recipe.RecipeViewState
import com.ssafy.sungchef.domain.viewstate.recommendation.RecommendationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getVisitRecipeUseCase: GetVisitRecipeUseCase
) : ViewModel() {
    private val initialState: RecipeViewState by lazy { createInitialState() }
    fun createInitialState(): RecipeViewState = RecipeViewState()

    val currentState: RecipeViewState get() = uiState.value

    private val _uiState: MutableStateFlow<RecipeViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<RecipeViewState> = _uiState

    fun getRecipeInfo(page: Int) {
        viewModelScope.launch {
            getVisitRecipeUseCase(page).collect{
                when(it){
                    is DataState.Success ->{
                        setState { currentState.copy(isLoading = false, recipeInfoList = it.data) }
                    }
                    is DataState.Error ->{
                        setState { currentState.copy(isLoading = false) }
                    }
                    is DataState.Loading ->{
                        setState { currentState.copy(isLoading = true) }
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