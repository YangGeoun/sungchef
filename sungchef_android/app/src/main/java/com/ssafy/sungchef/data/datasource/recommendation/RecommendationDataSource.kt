package com.ssafy.sungchef.data.datasource.recommendation

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendationResponse
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import com.ssafy.sungchef.data.model.responsedto.UserSettingInfoData

interface RecommendationDataSource {
    suspend fun getRecommendation(): DataState<ResponseDto<RecommendationResponse>>
    suspend fun getUser(): DataState<ResponseDto<UserSettingInfoData>>
}