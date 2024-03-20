package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recommendation.Recommendation
import kotlinx.coroutines.flow.Flow

interface RecommendationRepository {
    suspend fun getRecommendation(): Flow<DataState<Recommendation>>
}