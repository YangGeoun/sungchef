package com.ssafy.sungchef.domain.usecase.user

import android.util.Log
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.UserSettingInfo
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SettingUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    suspend fun getUserSettingInfo() : UserSettingInfo {
        return userRepository.userSettingInfo()
    }

}