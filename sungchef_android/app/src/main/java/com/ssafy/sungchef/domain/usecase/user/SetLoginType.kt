package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class SetLoginType @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend fun setLoginType(loginType : String) {
        userDataStoreRepository.setLoginType(loginType)
    }
}