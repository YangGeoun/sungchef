package com.ssafy.sungchef.data.datasource.recommendation

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.recommendation.RecommendationResponse
import com.ssafy.sungchef.data.model.responsedto.ResponseDto

interface RecommendationDataSource {
    suspend fun getRecommendation(): DataState<ResponseDto<RecommendationResponse>>
}