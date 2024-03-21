package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import retrofit2.Response

interface UserRepository {
    suspend fun duplicateNickname(nickname : String) : Response<APIError>
    suspend fun userSimple() : UserSimple

    suspend fun makeRecipeList(page : Int) : MakeRecipeList
}