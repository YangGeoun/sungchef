package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend fun autoLogin() : Flow<DataState<Boolean>> {
        return userRepository.autoLogin()
    }
}