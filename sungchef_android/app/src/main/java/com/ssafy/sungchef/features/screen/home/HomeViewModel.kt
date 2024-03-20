package com.ssafy.sungchef.features.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.usecase.RecommendationUseCase
import com.ssafy.sungchef.domain.viewstate.recommendation.RecommendationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recommendationUseCase: RecommendationUseCase
) : ViewModel() {

    private val initialState: RecommendationViewState by lazy { createInitialState() }
    fun createInitialState(): RecommendationViewState = RecommendationViewState()

    val currentState: RecommendationViewState get() = uiState.value

    private val _uiState: MutableStateFlow<RecommendationViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<RecommendationViewState> = _uiState

    fun getRecommendation() {
        viewModelScope.launch {
            recommendationUseCase().collect {
                when (it) {
                    is DataState.Success -> {
                        Log.d("TAG", "getRecommendation: ${it.data}")
                        setState {
                            currentState.copy(
                                isLoading = false,
                                recommendFood = it.data.recommendedFoodList,
                                recommendRecipe = it.data.recommendedRecipeList
                            )
                        }
                    }

                    is DataState.Loading -> {
                        setState { currentState.copy(isLoading = true) }
                    }

                    is DataState.Error -> {
                        setState { currentState.copy(isLoading = false) }
                        //Todo 새로 고침 하기
                    }
                }
            }
        }
    }

    private fun setState(reduce: RecommendationViewState.() -> RecommendationViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }
}