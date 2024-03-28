package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class GetUserSnsIdUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend fun getUserSnsId() : String {
        return userDataStoreRepository.getUserSnsId() ?: ""
    }
}