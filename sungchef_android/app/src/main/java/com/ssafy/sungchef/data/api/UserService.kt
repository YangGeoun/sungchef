package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.APIError
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList
import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @GET("user/exist/{nickname}")
    suspend fun duplicateNickname(@Path("nickname") nickname : String) : Response<APIError>

    @GET("user/simple")
    suspend fun userSimple() : UserSimple

    @GET("user/recipe/{page}")
    suspend fun makeRecipeList(@Path("page") page : Int) : MakeRecipeList

    @GET("user/bookmark/{page}")
    suspend fun bookmarkRecipeList(@Path("page") page : Int) : BookmarkRecipeList

    @POST("user/signup")
    suspend fun signupUser(@Body userRequestDTO: UserRequestDTO) : Response<ResponseDto<TokenResponse>>

    @POST("survey/submit")
    suspend fun submitSurvey(@Body surveyRequestDTO: SurveyRequestDTO) : Response<APIError>
}