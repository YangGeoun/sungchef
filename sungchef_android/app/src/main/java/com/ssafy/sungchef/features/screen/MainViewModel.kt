package com.ssafy.sungchef.features.screen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.usecase.token.GetTokenUseCase
import com.ssafy.sungchef.domain.usecase.user.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val autoLoginUseCase: AutoLoginUseCase
) : ViewModel(){

    private val _accessToken = MutableStateFlow("")
    val accessToken : StateFlow<String> = _accessToken

    private val _isSuccessAutoLogin = MutableStateFlow<Boolean?>(null)
    val isSuccessAutoLogin = _isSuccessAutoLogin.asStateFlow()

    init {
        autoLogin()
    }
    fun getToken() {
        viewModelScope.launch {
            getTokenUseCase.getToken()?.let { jwtToken ->
                _accessToken.emit(jwtToken.accessToken)
            }
        }
    }

    private fun autoLogin() {
        viewModelScope.launch{
            autoLoginUseCase.autoLogin().collect {
                when (it) {
                    is DataState.Success -> {
                        _isSuccessAutoLogin.emit(true)
                    }

                    is DataState.Loading -> {

                    }

                    is DataState.Error -> {
                        _isSuccessAutoLogin.emit(false)
                    }
                }
            }
        }
    }
}