package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.data.model.APIError
import retrofit2.Response

interface UserRepository {
    suspend fun duplicateNickname(nickname : String) : DataState<APIError>
    suspend fun userSimple() : UserSimple

    suspend fun makeRecipeList(page : Int) : MakeRecipeList
}