package com.ssafy.sungchef.features.screen.signup


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.SERVER_INSTABILITY
import com.ssafy.sungchef.commons.WRONG_NICKNAME_FORMAT
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.usecase.signup.DuplicateNicknameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SignupViewModel_성식당"
@HiltViewModel
class SignupViewModel @Inject constructor(
    private val duplicateNicknameUseCase: DuplicateNicknameUseCase
) : ViewModel(){

    val topBarNumber = mutableIntStateOf(1)
    val nickname: MutableState<String> = mutableStateOf("")
    val birth = mutableStateOf("")
    val gender = mutableStateOf("")

    private val _isDuplicateName = MutableStateFlow(BaseModel())
    val isDuplicateName : StateFlow<BaseModel> = _isDuplicateName

    private val _isError = MutableStateFlow(false)
    val isError : StateFlow<Boolean> = _isError

    private val _isNextPage = MutableStateFlow(false)
    val isNextPage : StateFlow<Boolean> = _isNextPage

    fun checkNickname(): Boolean {
        val length = nickname.value.length

        return length !in 2..10 && length != 0
    }

    fun checkBirth() : Boolean {
        return birth.value.isNotEmpty()
    }

    fun checkGender() : Boolean {
        return gender.value.isNotEmpty()
    }

    fun moveNextPage() {
        topBarNumber.intValue++
    }

    fun movePreviousPage() {
        topBarNumber.intValue--
    }

    fun isDuplicateNickname(nickname : String) {
        viewModelScope.launch {
            duplicateNicknameUseCase.duplicateNickname(nickname).collect{
                Log.d(TAG, "isDuplicateNickname: 넌 몇 번 호출되니?")
                when (it) {
                    is DataState.Success -> {
                        _isDuplicateName.emit( BaseModel(
                                it.data.isSuccess,
                                it.data.message
                            )
                        )
                        _isError.emit(false)
                        _isNextPage.emit(true)
                    }
                    is DataState.Error -> {
                        _isDuplicateName.emit( BaseModel(
                                false,
                                // 에러 코드에 따라 메시지가 바뀐다.
                                failNicknameMessage(it.apiError.code)
                            )
                        )
                        _isError.emit(true)
                        _isNextPage.emit(false)
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun failNicknameMessage(code : Long) : String {
        return when (code) {
            // 에러코드에 따른 메시지
            400L -> WRONG_NICKNAME_FORMAT
            409L -> ALREADY_NICKNAME
            else -> SERVER_INSTABILITY
        }
    }

    fun initIsNextPageState(state : Boolean) {
        _isNextPage.value = state
    }
}