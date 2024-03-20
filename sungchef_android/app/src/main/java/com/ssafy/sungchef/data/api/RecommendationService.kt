package com.ssafy.sungchef.data.api

import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendationResponse
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface RecommendationService {

    @GET("recommend")
    suspend fun getRecommendation(): Response<ResponseDto<RecommendationResponse>>
}