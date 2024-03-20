package com.ssafy.sungchef.data.datasource.recommendation

import com.ssafy.sungchef.data.api.RecommendationService
import com.ssafy.sungchef.data.datasource.BaseRemoteDataSource
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.model.responsedto.RecommendationResponse
import com.ssafy.sungchef.data.model.responsedto.ResponseDto
import javax.inject.Inject

class RecommendationDataSourceImpl @Inject constructor(
    private val recommendationService: RecommendationService
) : RecommendationDataSource, BaseRemoteDataSource() {
    override suspend fun getRecommendation(): DataState<ResponseDto<RecommendationResponse>> =
        getResult { recommendationService.getRecommendation() }
}