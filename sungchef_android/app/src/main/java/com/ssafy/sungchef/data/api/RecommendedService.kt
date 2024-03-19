package com.ssafy.sungchef.data.api

import retrofit2.http.GET

interface RecommendedService {

    @GET("recommend")
    suspend fun getRecommended()
}