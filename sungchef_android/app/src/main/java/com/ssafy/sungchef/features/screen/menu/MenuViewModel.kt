package com.ssafy.sungchef.features.screen.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.usecase.cooking.GetLackIngredientUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetBookMarkRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetDetailRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetSearchedBookmarkUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetSearchedVisitRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.GetVisitRecipeUseCase
import com.ssafy.sungchef.domain.usecase.recipe.SearchFoodNameUseCase
import com.ssafy.sungchef.domain.usecase.user.ChangeBookmarkRecipe
import com.ssafy.sungchef.domain.viewstate.recipe.RecipeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getVisitRecipeUseCase: GetVisitRecipeUseCase,
    private val getBookMarkRecipeUseCase: GetBookMarkRecipeUseCase,
    private val getDetailRecipeUseCase: GetDetailRecipeUseCase,
    private val changeBookmarkRecipe: ChangeBookmarkRecipe,
    private val getLackIngredientUseCase: GetLackIngredientUseCase,
    private val searchFoodNameUseCase: SearchFoodNameUseCase,
    private val getSearchedVisitRecipeUseCase: GetSearchedVisitRecipeUseCase,
    private val getSearchedBookmarkUseCase: GetSearchedBookmarkUseCase
) : ViewModel() {
    private val initialState: RecipeViewState by lazy { RecipeViewState() }
    private val currentState: RecipeViewState get() = uiState.value
    private val _uiState: MutableStateFlow<RecipeViewState> = MutableStateFlow(initialState)
    val uiState: StateFlow<RecipeViewState> = _uiState

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
        getVisitRecipeInfo(0)
    }

    fun getBookMarkRecipeInfo(page: Int) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            val pagedFlow = getBookMarkRecipeUseCase(page).cachedIn(viewModelScope)
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
                        setState { currentState.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun getLackIngredient(id: Int) {
        viewModelScope.launch {
            getLackIngredientUseCase(id).collect() {
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

    private fun setState(reduce: RecipeViewState.() -> RecipeViewState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

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