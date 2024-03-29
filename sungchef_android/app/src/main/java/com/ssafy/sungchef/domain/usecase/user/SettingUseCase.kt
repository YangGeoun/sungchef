package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.ContactRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserUpdateRequestDTO
import com.ssafy.sungchef.data.model.responsedto.UserSettingInfo
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import com.ssafy.sungchef.domain.repository.UserRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class SettingUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userDataStoreRepository: UserDataStoreRepository
){

    suspend fun getUserSettingInfo() : UserSettingInfo {
        return userRepository.userSettingInfo()
    }

    suspend fun setEmail(email : String){
        userDataStoreRepository.setEmail(email)
    }
    suspend fun getEmail() : String {
        return userDataStoreRepository.getEmail() ?: ""
    }
    suspend fun inquire(contactRequestDTO: ContactRequestDTO) : Response<APIError> {
        return userRepository.inquire(contactRequestDTO)
    }
    suspend fun updateUserInfo(userImage : MultipartBody.Part?, userUpdateRequestDTO: UserUpdateRequestDTO) : Response<APIError> {
        return userRepository.updateUserInfo(userImage, userUpdateRequestDTO)
    }
}