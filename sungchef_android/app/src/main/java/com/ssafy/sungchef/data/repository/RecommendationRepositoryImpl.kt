package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.data.datasource.recommendation.RecommendationDataSource
import com.ssafy.sungchef.data.mapper.recommendation.toRecommendation
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recommendation.Recommendation
import com.ssafy.sungchef.domain.repository.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val recommendationDataSource: RecommendationDataSource
): RecommendationRepository {
    override suspend fun getRecommendation(): Flow<DataState<Recommendation>> =
        flow {
            val recommendation = recommendationDataSource.getRecommendation()
            if (recommendation is DataState.Success){
                emit(DataState.Success(recommendation.data.data.toRecommendation()))
            }
        }
}