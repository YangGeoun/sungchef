package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class GetUserLoginTypeUseCase  @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
){
    suspend fun getUserLoginType() : String {
        return userDataStoreRepository.getLoginType() ?: ""
    }
}