package com.ssafy.sungchef.features.screen.refrigerator.receipt.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.domain.usecase.refrigerator.SearchIngredientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterIngredientViewModel @Inject constructor(
    private val searchIngredientUseCase: SearchIngredientUseCase
) : ViewModel(){

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchIngredientList = MutableStateFlow<List<SearchIngredient>>(listOf())
    val searchIngredientList = _searchIngredientList.asStateFlow()

//    private val _ingredientList = MutableStateFlow<Map<>>

    private var searchJob: Job? = null

    fun onSearchTextChange(text: String) {
        _searchText.value = text

        searchJob?.cancel() // 이전에 실행 중이던 검색 작업이 있다면 취소
        searchJob = viewModelScope.launch {
            if (text.isBlank()) {
                _searchIngredientList.value = emptyList()
                return@launch
            }
            delay(500) // Debouncing
            searchIngredient(text)
        }
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun searchIngredient(ingredientName : String) {
        viewModelScope.launch {
            searchIngredientUseCase.searchIngredient(ingredientName).collect {
                when (it) {
                    is DataState.Success -> {
                        _searchIngredientList.emit(it.data)
                    }

                    is DataState.Loading -> {

                    }

                    is DataState.Error -> {

                    }
                }
            }
        }
    }
}