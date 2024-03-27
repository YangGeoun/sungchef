package com.ssafy.sungchef.domain.usecase.signup

import android.util.Log
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "SignupUserUseCase_성식당"
class SignupUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun signupUser(userRequestDTO: UserRequestDTO) : Flow<DataState<Int>> {
        return userRepository.signupUser(userRequestDTO)
    }
}