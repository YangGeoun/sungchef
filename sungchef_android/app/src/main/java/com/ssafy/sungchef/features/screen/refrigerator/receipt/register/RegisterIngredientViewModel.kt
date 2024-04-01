package com.ssafy.sungchef.features.screen.refrigerator.receipt.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.domain.usecase.refrigerator.RegisterIngredientUseCase
import com.ssafy.sungchef.domain.usecase.refrigerator.SearchIngredientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RegisterIngredientViewM_성식당"
@HiltViewModel
class RegisterIngredientViewModel @Inject constructor(
    private val searchIngredientUseCase: SearchIngredientUseCase,
    private val registerIngredientUseCase: RegisterIngredientUseCase
) : ViewModel(){

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchIngredientList = MutableStateFlow<List<SearchIngredient>>(listOf())
    val searchIngredientList = _searchIngredientList.asStateFlow()

    private val _ingredientList = MutableStateFlow<Map<String, List<SearchIngredient>>>(mutableMapOf())
    val ingredientList = _ingredientList.asStateFlow()

    // 추가된 재료의 id를 관리 하는 리스트
    private val _ingredientIdList = MutableStateFlow<List<Int>>(listOf())
    val ingredientIdList = _ingredientIdList.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchTextChange(text: String) {
        _searchText.value = text

        searchJob?.cancel() // 이전에 실행 중이던 검색 작업이 있다면 취소
        searchJob = viewModelScope.launch {
            if (text.isBlank()) {
                _searchIngredientList.value = emptyList()
                return@launch
            }
            delay(300) // Debouncing
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

    /**
     * 재료를 검색하여 Map에 추가하고 화면에 보여주기
     */
    fun addIngredient(ingredient : SearchIngredient) {
        val type = ingredient.ingredientType

        // 기존 맵을 복사하여 새로운 맵을 할당
        val updateMap = _ingredientList.value.toMutableMap().apply {

            // 리스트도 새로운 리스트로 만들어야 StateFlow가 인식함
            val list = _ingredientList.value.getOrDefault(type, mutableListOf()).toMutableList()
            list.add(ingredient)
            this[type] = list
        }
        _ingredientList.value = updateMap

        // 더하기 연산자로 리스트에 요소를 추가 할 수 있음
        // 기존 리스트에 ingredientId가 있으면 추가하지 않음
        if (!_ingredientIdList.value.contains(ingredient.ingredientId)) {
            _ingredientIdList.value = _ingredientIdList.value + ingredient.ingredientId
        }
    }

    /**
     * 추가한 재료를 삭제하고 화면에 보여주기
     */
    fun deleteIngredient(ingredient : SearchIngredient) {

        val type = ingredient.ingredientType

        // _ingredientList.value에서 type에 해당하는 리스트를 가져온다.
        // 가져온 리스트가 null이 아니라면, 해당 ingredient를 리스트에서 제거한다.
        _ingredientList.value[type]?.let { currentList ->
            val updatedList = currentList.filter { it.ingredientId != ingredient.ingredientId }

            // 수정 가능한 Map을 생성하여 업데이트한다.
            val updatedMap = _ingredientList.value.toMutableMap().apply {
                // 업데이트된 리스트가 비어있지 않다면, Map을 업데이트한다.
                if (updatedList.isNotEmpty()) {
                    this[type] = updatedList
                } else {
                    // 리스트가 비었다면, 해당 키를 Map에서 제거한다.
                    this.remove(type)
                }
            }

            // _ingredientList에 업데이트된 Map을 할당한다.
            _ingredientList.value = updatedMap
        }
        _ingredientIdList.value = _ingredientIdList.value.filter { it != ingredient.ingredientId }
    }

    fun registerIngredient() {
        viewModelScope.launch {
            registerIngredientUseCase.registerIngredient(_ingredientIdList.value).collect {
                when (it) {
                    is DataState.Success -> {
                        Log.d(TAG, "registerSuccess: ${it.data.code}")
                    }

                    is DataState.Loading -> {

                    }

                    is DataState.Error -> {
                        Log.d(TAG, "registerSuccess: ${it.apiError.code}")
                    }
                }
            }
        }
    }
}