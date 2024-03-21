package com.ssafy.sungchef.data.datasource.user

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import retrofit2.Response

interface UserDataSource {

    suspend fun duplicateNickname(nickname : String) : DataState<APIError>

}