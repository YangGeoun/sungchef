package com.ssafy.sungchef.features.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.user.LoginState
import com.ssafy.sungchef.domain.usecase.user.GetLoginState
import com.ssafy.sungchef.domain.usecase.user.SetLoginType
import com.ssafy.sungchef.domain.usecase.user.SetUserSnsIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginState: GetLoginState,
    private val setLoginType : SetLoginType,
    private val setUserSnsIdUseCase: SetUserSnsIdUseCase
) : ViewModel(){

    private val _loginState = MutableStateFlow(LoginState())
    val loginState : StateFlow<LoginState> = _loginState

    private val _needSurvey = MutableStateFlow(false)
    val needSurvey = _needSurvey.asStateFlow()

    fun login(userSnsIdRequestDTO : UserSnsIdRequestDTO, loginType : String) {
        viewModelScope.launch {
            setLoginType.setLoginType(loginType)   // 로그인 타입 저장
            setUserSnsIdUseCase.setUserSnsId(userSnsIdRequestDTO.userSnsId) // uid 저장
            getLoginState.getLoginStateCode(userSnsIdRequestDTO).collect {
                when (it) {
                    is DataState.Success -> {
                        _loginState.emit(it.data)
                        _needSurvey.emit(it.data.needSurvey)
                    }

                    is DataState.Error -> {
                        _loginState.emit(
                            LoginState(
                                code = it.apiError.code.toInt(),
                                message = it.apiError.message,
                                isLoading = false
                            )
                        )
                    }

                    is DataState.Loading -> {
                        _loginState.emit(
                            LoginState(
                                isLoading = true
                            )
                        )
                        delay(5000)
                    }
                }
            }
        }
    }

    fun initLoginStateCode(){
        _loginState.value = LoginState()
    }
}