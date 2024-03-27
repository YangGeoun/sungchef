package com.ssafy.sungchef.domain.usecase.token

import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class DeleteTokenUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {

    suspend fun deleteToken() {
        userDataStoreRepository.deleteToken()
    }
}