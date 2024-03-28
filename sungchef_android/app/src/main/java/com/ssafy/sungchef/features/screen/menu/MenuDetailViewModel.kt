package com.ssafy.sungchef.features.screen.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.usecase.cooking.GetLackIngredientUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetDetailRecipeUseCase
import com.ssafy.sungchef.domain.viewstate.recipe.RecipeDetailViewState
import com.ssafy.sungchef.domain.viewstate.recipe.RecipeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuDetailViewModel @Inject constructor(
    private val getLackIngredientUseCase: GetLackIngredientUseCase,
    private val getDetailRecipeUseCase: GetDetailRecipeUseCase,
):ViewModel() {

    private val initialState: RecipeDetailViewState by lazy { RecipeDetailViewState() }
    private val currentState: RecipeDetailViewState get() = uiState.value
    private val _uiState: MutableStateFlow<RecipeDetailViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<RecipeDetailViewState> = _uiState

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
                            404.toLong() -> setState { currentState.copy(isError = true) }
                        }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun getLackIngredient(id: Int) {
        viewModelScope.launch {
            getLackIngredientUseCase(id).collect(){
                when (it) {
                    is DataState.Success -> {
                        Log.d("TAG", "getLackIngredient: ${it.data.ingredientInfo}")
                        setState { currentState.copy(isLoading = false, lackIngredient = it.data) }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isError = true, isLoading = false) }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun setState(reduce: RecipeDetailViewState.() -> RecipeDetailViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }
}