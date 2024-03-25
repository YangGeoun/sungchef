package com.ssafy.sungchef.features.screen.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.SERVER_INSTABILITY
import com.ssafy.sungchef.commons.WRONG_NICKNAME_FORMAT
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeListData
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeListData
import com.ssafy.sungchef.data.model.responsedto.UserProfile
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.usecase.signup.DuplicateNicknameUseCase
import com.ssafy.sungchef.domain.usecase.user.SettingUseCase
import com.ssafy.sungchef.domain.usecase.user.UserSimpleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
    private val duplicateNicknameUseCase: DuplicateNicknameUseCase
) : ViewModel() {


    private val _userNickname = MutableStateFlow<String>("")
    val userNickname : StateFlow<String> = _userNickname.asStateFlow()

    private val _isDuplicateName = MutableStateFlow(BaseModel())
    val isDuplicateName : StateFlow<BaseModel> = _isDuplicateName

    private val _isDuplicateCheckNeeded = MutableStateFlow<Boolean>(false)
    val isDuplicateCheckNeeded : StateFlow<Boolean> = _isDuplicateCheckNeeded


//    init {
//        getUserSimple()
//    }

    fun getUserSettingInfo() {
        viewModelScope.launch {
            val userSettingInfo = settingUseCase.getUserSettingInfo()
            Log.d(TAG, "getUserSettingInfo: $userSettingInfo")
            _userNickname.value = userSettingInfo.data.userNickName
        }
    }

    fun setNickname(newNickname : String){
//        Log.d(TAG, "setNickname: $newNickname")
        _userNickname.value = newNickname
    }

    fun setIsDuplicateCheckNeeded(check : Boolean){
        _isDuplicateCheckNeeded.value = check
    }

    fun checkNickname(): Boolean {
        val length = userNickname.value.length

        return length !in 2..10 && length != 0
    }
    fun isDuplicateNickname(nickname : String) {
        viewModelScope.launch {
            duplicateNicknameUseCase.duplicateNickname(nickname).collect{
                when (it) {
                    is DataState.Success -> {
                        _isDuplicateName.emit( BaseModel(
                            it.data.isSuccess,
                            it.data.message
                        )
                        )
                    }
                    is DataState.Error -> {
                        _isDuplicateName.emit( BaseModel(
                            false,
                            // 에러 코드에 따라 메시지가 바뀐다.
                            failNicknameMessage(it.apiError.code)
                        )
                        )
                    }
                    is DataState.Loading -> {

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

}