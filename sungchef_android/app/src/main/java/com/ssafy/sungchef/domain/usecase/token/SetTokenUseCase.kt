package com.ssafy.sungchef.domain.usecase.token

import com.ssafy.sungchef.domain.model.token.JwtToken
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {

    suspend fun setToken(token : JwtToken) {
        userDataStoreRepository.setToken(token)
    }
}