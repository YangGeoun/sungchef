package com.ssafy.sungchef.features.screen.refrigerator.receipt.start

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.FAIL_OCR
import com.ssafy.sungchef.domain.model.refrigerator.RegisterReceiptState
import com.ssafy.sungchef.domain.usecase.refrigerator.RegisterReceiptUseCase
import com.ssafy.sungchef.domain.viewstate.recipe.RecipeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StartReceiptViewModel_성식당"
@HiltViewModel
class StartReceiptViewModel @Inject constructor(
    private val registerReceiptUseCase: RegisterReceiptUseCase
) : ViewModel(){

    private val currentState: RegisterReceiptState get() = uiState.value

    private val _uiState = MutableStateFlow(RegisterReceiptState())
    val uiState = _uiState.asStateFlow()

    fun registerReceipt(imageUri : Uri, context : Context) {
        viewModelScope.launch {
            setState { currentState.copy(isLoading = true) }
            registerReceiptUseCase.registerReceipt(imageUri, context).collect {
                when (it) {
                    is DataState.Success -> {
                        Log.d(TAG, "registerReceipt: ${it.data.code}")

                        _uiState.emit(
                            RegisterReceiptState(
                                isLoading = false,
                                code = it.data.code,
                                dialogTitle = it.data.dialogTitle,
                                imageUrl = it.data.imageUrl
                            )
                        )
                    }

                    is DataState.Loading -> {
                        _uiState.emit(
                            RegisterReceiptState(
                                isLoading = true
                            )
                        )
                        Log.d(TAG, "registerReceipt: 로딩 중")
                        Log.d(TAG, "registerReceipt: ${_uiState.value}")
                    }

                    is DataState.Error -> {
                        Log.d(TAG, "registerReceipt: ${it.apiError.code}")

                        _uiState.emit(
                            RegisterReceiptState(
                                code = it.apiError.code.toInt(),
                                dialogTitle = FAIL_OCR,
                                isLoading = false
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setState(reduce: RegisterReceiptState.() -> RegisterReceiptState) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }
}