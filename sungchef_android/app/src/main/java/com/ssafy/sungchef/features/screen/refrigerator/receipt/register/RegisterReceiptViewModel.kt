package com.ssafy.sungchef.features.screen.refrigerator.receipt.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.RETRY_SEARCH
import com.ssafy.sungchef.commons.SERVER_INSTABILITY
import com.ssafy.sungchef.data.model.responsedto.ocr.ConvertInfo
import com.ssafy.sungchef.domain.model.refrigerator.RegisterIngredientState
import com.ssafy.sungchef.domain.model.refrigerator.SearchIngredient
import com.ssafy.sungchef.domain.usecase.refrigerator.GetOcrIngredientUseCase
import com.ssafy.sungchef.domain.usecase.refrigerator.RegisterIngredientUseCase
import com.ssafy.sungchef.domain.usecase.refrigerator.SearchIngredientUseCase
import com.ssafy.sungchef.util.IngredientType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RegisterReceiptViewMode_성식당"
@HiltViewModel
class RegisterReceiptViewModel @Inject constructor(
    private val getOcrIngredientUseCase: GetOcrIngredientUseCase,
    private val searchIngredientUseCase : SearchIngredientUseCase,
    private val registerIngredientUseCase: RegisterIngredientUseCase
) : ViewModel(){

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _ingredientMap = MutableStateFlow(mapOf<String, List<ConvertInfo>>())
    val ingredientMap = _ingredientMap.asStateFlow()

    private val _ingredientIdList = MutableStateFlow(listOf<Int>())
    val ingredientIdList = _ingredientIdList.asStateFlow()

    private val _isRegister = MutableStateFlow(false)
    val isRegister = _isRegister.asStateFlow()

    private val _searchIngredientList = MutableStateFlow<List<SearchIngredient>>(listOf())
    val searchIngredientList = _searchIngredientList.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterIngredientState())
    val registerState = _registerState.asStateFlow()

    private var searchJob: Job? = null

    var notConvertedIngredient : Pair<String, ConvertInfo>? = null

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

    fun getOcrIngredient(convertOCRKey : String) {
        viewModelScope.launch {
            getOcrIngredientUseCase.getOcrIngredient(convertOCRKey).collect {
                when (it) {
                    is DataState.Success -> {
                        Log.d(TAG, "getOcrIngredient: ${it.data.toString()}")
                        _ingredientMap.emit(it.data)
                        makeIngredientIdList(it.data)
                    }

                    is DataState.Loading -> {

                    }

                    is DataState.Error -> {

                    }
                }
            }
        }
    }

    fun registerIngredient() {
        viewModelScope.launch {
            registerIngredientUseCase.registerIngredient(_ingredientIdList.value).collect {
                when (it) {
                    is DataState.Success -> {
                        Log.d(TAG, "registerIngredient: ${it.data}")
                        _registerState.emit(
                            RegisterIngredientState(
                                it.data.code.toInt()
                            )
                        )
                    }

                    is DataState.Loading -> {

                    }

                    is DataState.Error -> {
                        when (it.apiError.code) {
                            400L -> {
                                _registerState.emit(
                                    RegisterIngredientState(400, RETRY_SEARCH)
                                )
                            }

                            500L -> {
                                _registerState.emit(
                                    RegisterIngredientState(500, SERVER_INSTABILITY)
                                )
                            }

                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun makeIngredientIdList(data : Map<String, List<ConvertInfo>>) {
        // 모든 map의 리스트를 하나의 리스트로 평탄화
        val ingredientIdList = data.values.flatten().map {it.ingredientId}
        Log.d(TAG, "ingredientIdList: $ingredientIdList")
        _ingredientIdList.value = ingredientIdList
    }

    fun addIngredient(ingredient : SearchIngredient) {
        val type = IngredientType.transformToEnglish(ingredient.ingredientType)

        // 기존 맵을 복사하여 새로운 맵을 할당
        val updateMap = _ingredientMap.value.toMutableMap().apply {

            // 리스트도 새로운 리스트로 만들어야 StateFlow가 인식함
            val list = _ingredientMap.value.getOrDefault(type, mutableListOf()).toMutableList()
            val convertInfo = ConvertInfo(
                ingredient.ingredientName,
                ingredient.ingredientId,
                true
            )
            list.add(convertInfo)
            this[type] = list
        }
        _ingredientMap.value = updateMap

        // 더하기 연산자로 리스트에 요소를 추가 할 수 있음
        // 기존 리스트에 ingredientId가 있으면 추가하지 않음
        if (!_ingredientIdList.value.contains(ingredient.ingredientId)) {
            _ingredientIdList.value = _ingredientIdList.value + ingredient.ingredientId
        }

        notConvertedIngredient?.let {
            val convertType = it.first
            val convertInfo = it.second

            deleteIngredient(convertType, convertInfo)

            notConvertedIngredient = null
        }
    }

    fun deleteIngredient(type : String, item : ConvertInfo) {
        _ingredientMap.value[type]?.let { currentList ->
            val updateList = currentList.filter { it.ingredientId != item.ingredientId }

            val updateMap = _ingredientMap.value.toMutableMap().apply {

                if (updateList.isNotEmpty()) {
                    this[type] = updateList
                } else {
                    this.remove(type)
                }
            }
            _ingredientMap.value = updateMap
        }
        _ingredientIdList.value = _ingredientIdList.value.filter {it != item.ingredientId}
    }
}