package com.ssafy.sungchef.domain.usecase

import android.util.Log
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserSimpleUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend fun userSimple() : UserSimple {
        return userRepository.userSimple()
    }

    suspend fun makeRecipeList(page : Int) : MakeRecipeList {
        return userRepository.makeRecipeList(page)
    }
    suspend fun bookmarkRecipeList(page : Int) : BookmarkRecipeList {
        return userRepository.bookmarkRecipeList(page)
    }
}