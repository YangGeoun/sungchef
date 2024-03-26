package com.ssafy.sungchef.features.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.domain.usecase.user.GetLoginState
import com.ssafy.sungchef.domain.usecase.user.SetLoginType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginState: GetLoginState,
    private val setLoginType : SetLoginType
) : ViewModel(){

    private val _loginState = MutableStateFlow(LoginState())
    val loginState : StateFlow<LoginState> = _loginState

    fun login(userSnsIdRequestDTO : UserSnsIdRequestDTO, loginType : String) {
        viewModelScope.launch {
            setLoginType.setLoginType(loginType)
            getLoginState.getLoginStateCode(userSnsIdRequestDTO).collect {
                when (it) {
                    is DataState.Success -> {
                        _loginState.emit(it.data)
                    }

                    is DataState.Error -> {
                        _loginState.emit(
                            LoginState(
                                it.apiError.code.toInt(),
                                it.apiError.message
                            )
                        )
                    }

                    is DataState.Loading -> {

                    }
                }
            }
        }
    }

    fun initLoginStateCode(){
        _loginState.value = LoginState()
    }
}