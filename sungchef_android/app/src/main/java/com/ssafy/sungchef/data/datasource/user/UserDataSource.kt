package com.ssafy.sungchef.data.datasource.user

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.data.model.responsedto.survey.SurveyResponse
import retrofit2.Response

interface UserDataSource {

    suspend fun duplicateNickname(nickname : String) : DataState<APIError>

    suspend fun userSimple() : UserSimple
    suspend fun makeRecipeList(page : Int) : MakeRecipeList;
    suspend fun bookmarkRecipeList(page : Int) : BookmarkRecipeList

    suspend fun surveySubmit(surveyRequestDTO: SurveyRequestDTO) : DataState<APIError>

    suspend fun getSurvey() : DataState<ResponseDto<SurveyResponse>>
}