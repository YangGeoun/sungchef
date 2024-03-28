package com.ssafy.sungchef.domain.usecase.user

import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class SetUserSnsIdUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend fun setUserSnsId(userSnsId: String) {
        userDataStoreRepository.setUserSnsId(userSnsId)
    }
}