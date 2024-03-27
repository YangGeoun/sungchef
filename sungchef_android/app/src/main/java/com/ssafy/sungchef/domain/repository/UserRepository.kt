package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.ContactRequestDTO
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.UserSettingInfo
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import com.ssafy.sungchef.domain.model.base.BaseModel
import com.ssafy.sungchef.domain.model.survey.Survey
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface UserRepository {
    suspend fun duplicateNickname(nickname : String) : Flow<DataState<BaseModel>>
    suspend fun userSimple() : UserSimple

    suspend fun makeRecipeList(page : Int) : MakeRecipeList
    suspend fun bookmarkRecipeList(page : Int) : BookmarkRecipeList

    suspend fun changeBookmarkRecipe(recipeId:Int, isBookmark:Boolean): Flow<DataState<BaseModel>>


    suspend fun surveySubmit(selectSurveyList : List<Int>) : Flow<DataState<Boolean>>

    suspend fun getSurvey() : Flow<DataState<Survey>>

    suspend fun userSettingInfo() : UserSettingInfo


    suspend fun inquire(contactRequestDTO: ContactRequestDTO): Response<APIError>
}