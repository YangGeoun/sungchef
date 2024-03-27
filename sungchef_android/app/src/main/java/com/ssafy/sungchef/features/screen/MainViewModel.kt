package com.ssafy.sungchef.features.screen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.domain.usecase.token.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel(){

    private val _accessToken = MutableStateFlow("")
    val accessToken : StateFlow<String> = _accessToken
    fun getToken() {
        viewModelScope.launch {
            getTokenUseCase.getToken()?.let { jwtToken ->
                _accessToken.emit(jwtToken.accessToken)
            }
        }
    }
}