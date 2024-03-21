package com.ssafy.sungchef.domain.usecase.signup

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class DuplicateNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend fun duplicateNickname(nickname : String) : Flow<DataState<BaseModel>> {
        return userRepository.duplicateNickname(nickname)
    }
}