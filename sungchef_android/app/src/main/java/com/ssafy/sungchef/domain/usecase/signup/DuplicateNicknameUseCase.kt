package com.ssafy.sungchef.domain.usecase.signup

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class DuplicateNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend fun duplicateNickname(nickname : String) : Response<APIError> {
        return userRepository.duplicateNickname(nickname)
    }
}