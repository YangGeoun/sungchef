package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.APIError

import com.ssafy.sungchef.data.model.requestdto.BookMarkRequest
import com.ssafy.sungchef.data.model.requestdto.ContactRequestDTO
import com.ssafy.sungchef.data.model.requestdto.SurveyRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserSnsIdRequestDTO
import com.ssafy.sungchef.data.model.requestdto.UserUpdateRequestDTO
import com.ssafy.sungchef.data.model.responsedto.BookmarkRecipeList

import com.ssafy.sungchef.data.model.responsedto.FridgeData

import com.ssafy.sungchef.data.model.responsedto.MakeRecipeList

import com.ssafy.sungchef.data.model.responsedto.ResponseDto

import com.ssafy.sungchef.data.model.responsedto.UserSettingInfo

import com.ssafy.sungchef.data.model.responsedto.UserSimple
import com.ssafy.sungchef.data.model.responsedto.survey.SurveyResponse
import com.ssafy.sungchef.data.model.responsedto.token.TokenResponse
import com.ssafy.sungchef.data.model.responsedto.user.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
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

    @POST("user/bookmark")
    suspend fun changeBookmarkRecipe(@Body bookMarkRequest: BookMarkRequest): Response<APIError>


    @POST("user/signup")
    suspend fun signupUser(@Body userRequestDTO: UserRequestDTO) : Response<ResponseDto<TokenResponse>>

    @POST("user/survey/submit")
    suspend fun submitSurvey(@Body surveyRequestDTO: SurveyRequestDTO) : Response<APIError>

    @GET("user/survey")
    suspend fun getSurvey() : Response<ResponseDto<SurveyResponse>>

    @GET("user")
    suspend fun userSettingInfo() : UserSettingInfo

    @POST("user/login")
    suspend fun login(@Body userSnsIdRequestDTO: UserSnsIdRequestDTO) : Response<ResponseDto<LoginResponse>>
    @POST("user/contact")
    suspend fun userContact(@Body contactRequestDTO: ContactRequestDTO) : Response<APIError>

    @Multipart
    @PUT("/user")
    suspend fun updateUserInfo(
        @Part userImage: MultipartBody.Part?,
        @PartMap data: HashMap<String, RequestBody>
    ): Response<APIError>

    @POST("user/autologin")
    suspend fun autoLogin() : Response<APIError>
}