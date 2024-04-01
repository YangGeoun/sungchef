package com.ssafy.sungchef.features.screen.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recipe.RecipeInfo
import com.ssafy.sungchef.domain.usecase.recipe.GetBookMarkRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetSearchedBookmarkUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetSearchedVisitRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetVisitRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.SearchFoodNameUseCase
import com.ssafy.sungchef.domain.usecase.user.ChangeBookmarkRecipeUseCase
import com.ssafy.sungchef.domain.viewstate.recipe.RecipeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getVisitRecipeUseCase: GetVisitRecipeUseCase,
    private val getBookMarkRecipeUseCase: GetBookMarkRecipeUseCase,
    private val changeBookmarkRecipeUseCase: ChangeBookmarkRecipeUseCase,
    private val searchFoodNameUseCase: SearchFoodNameUseCase,
    private val getSearchedVisitRecipeUseCase: GetSearchedVisitRecipeUseCase,
    private val getSearchedBookmarkUseCase: GetSearchedBookmarkUseCase
) : ViewModel() {
    private val initialState: RecipeViewState by lazy { RecipeViewState() }
    private val currentState: RecipeViewState get() = uiState.value
    private val _uiState: MutableStateFlow<RecipeViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<RecipeViewState> = _uiState

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun getVisitRecipeInfo(page: Int) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val pagedFlow = getVisitRecipeUseCase(page).cachedIn(viewModelScope)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }

    fun getSearchedVisitRecipeInfo(page: Int, text: String) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val pagedFlow = getSearchedVisitRecipeUseCase(page, text).cachedIn(viewModelScope)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }

    fun getSearchedBookmarkRecipeInfo(page: Int, text: String) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val pagedFlow = getSearchedBookmarkUseCase(page, text).cachedIn(viewModelScope)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }

    init {
        getSearchedVisitRecipeInfo(0, _searchText.value)
    }

    fun getBookMarkRecipeInfo(page: Int) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val pagedFlow = getBookMarkRecipeUseCase(page).cachedIn(viewModelScope)
            setState { currentState.copy(isLoading = false, pagedData = pagedFlow) }
        }
    }

    fun changeBookmarkRecipe(recipeId: Int, isBookmark: Boolean) {
        viewModelScope.launch {
            changeBookmarkRecipeUseCase(recipeId, isBookmark).collect {
                when (it) {
                    is DataState.Success -> {
                        setState { currentState.copy(isLoading = false) }
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

    private fun setState(reduce: RecipeViewState.() -> RecipeViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        if (text != "") {
            searchMenu(text)
        } else {
            setState { currentState.copy(foodList = listOf()) }
        }
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
    }

    fun searchMenu(text: String) {
        viewModelScope.launch {
            searchFoodNameUseCase(text).collect {
                when (it) {
                    is DataState.Success -> {
                        Log.d("TAG", "searchMenu:${it.data}")
                        setState { currentState.copy(foodList = it.data) }
                    }

                    is DataState.Error -> {

                    }

                    is DataState.Loading -> {

                    }
                }
            }
        }
    }
}