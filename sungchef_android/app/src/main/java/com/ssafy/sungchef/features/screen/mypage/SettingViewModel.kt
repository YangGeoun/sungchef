package com.ssafy.sungchef.features.screen.mypage

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.sungchef.commons.ALREADY_NICKNAME
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.commons.SERVER_INSTABILITY
import com.ssafy.sungchef.commons.WRONG_NICKNAME_FORMAT
import com.ssafy.sungchef.data.model.requestdto.ContactRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserUpdateRequestDTO
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.usecase.signup.DuplicateNicknameUseCase
import com.ssafy.sungchef.domain.usecase.user.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCase: SettingUseCase,
    private val duplicateNicknameUseCase: DuplicateNicknameUseCase
) : ViewModel() {

    private val _userProfileImage = MutableStateFlow<String?>("")
    val userProfileImage : StateFlow<String?> = _userProfileImage.asStateFlow()

    private val _userNickname = MutableStateFlow<String>("")
    val userNickname : StateFlow<String> = _userNickname.asStateFlow()

    private val _userBirthDate = MutableStateFlow<String>("2000-01-01")
    val userBirthDate : StateFlow<String> = _userBirthDate.asStateFlow()

    private val _userGender = MutableStateFlow<Boolean>(true)
    val userGender : StateFlow<Boolean> = _userGender.asStateFlow()

    private val _userEmail = MutableStateFlow<String>("")
    val userEmail : StateFlow<String> = _userEmail.asStateFlow()

    private val _isDuplicateName = MutableStateFlow(BaseModel())
    val isDuplicateName : StateFlow<BaseModel> = _isDuplicateName

    private val _isDuplicateCheckNeeded = MutableStateFlow<Boolean>(false)
    val isDuplicateCheckNeeded : StateFlow<Boolean> = _isDuplicateCheckNeeded
    var isPictureChanged : MutableStateFlow<Boolean> = MutableStateFlow(false);


//    init {
//        getUserSimple()
//    }

    fun updateUserSettingInfo(context : Context) {
        var multipart : MultipartBody.Part? = null
        if(isPictureChanged.value){
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(Uri.parse(userProfileImage.value))
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir).apply {
                outputStream().use { fileOut ->
                    inputStream?.copyTo(fileOut)
                }
            }
            val requestFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            multipart = MultipartBody.Part.createFormData("userImage", tempFile.name, requestFile)
            Log.d(TAG, "updateUserSettingInfo: $multipart")
        }

        val updateRequestDTO = UserUpdateRequestDTO(multipart, userNickname.value, if(userGender.value==true) 'M' else 'F', userBirthDate.value)
        viewModelScope.launch {
            settingUseCase.updateUserInfo(updateRequestDTO)
        }
    }

    fun getUserSettingInfo() {
        viewModelScope.launch {
            val userSettingInfo = settingUseCase.getUserSettingInfo()
            Log.d(TAG, "getUserSettingInfo: $userSettingInfo")
            _userProfileImage.value = userSettingInfo.data.userImage
            _userNickname.value = userSettingInfo.data.userNickName
            _userBirthDate.value = userSettingInfo.data.userBirthdate
            if(userSettingInfo.data.userGender.equals("M")) _userGender.value = true
            else _userGender.value = false
        }
    }

    fun setNickname(newNickname : String){
//        Log.d(TAG, "setNickname: $newNickname")
        _userNickname.value = newNickname
    }

    fun setIsDuplicateCheckNeeded(check : Boolean){
        _isDuplicateCheckNeeded.value = check
    }

    fun setBirthDate(newBirthDate : String){
//        Log.d(TAG, "setBirthDate: $newNickname")
        _userBirthDate.value = newBirthDate
    }

    fun setProfileImage(newProfileImage : String){
//        Log.d(TAG, "setProfileImage: $newNickname")
        _userProfileImage.value = newProfileImage
        isPictureChanged.value = true
    }

    fun setGender(newGender : Boolean){
//        Log.d(TAG, "setGender: newGender")
        _userGender.value = newGender
    }

    fun getEmail(){
        viewModelScope.launch {
            _userEmail.value = settingUseCase.getEmail()
        }
    }

    fun setEmail(email : String) {
        viewModelScope.launch {
            settingUseCase.setEmail(email)
        }
    }

    fun inquire(email : String, detail : String){
        viewModelScope.launch {
            settingUseCase.inquire(ContactRequestDTO(email, detail))
        }
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