package com.ssafy.sungchef.domain.repository

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recommendation.Recommendation
import com.ssafy.sungchef.domain.model.recommendation.UserInfo
import kotlinx.coroutines.flow.Flow

interface RecommendationRepository {
    suspend fun getRecommendation(): Flow<DataState<Recommendation>>
    suspend fun getUser():Flow<DataState<UserInfo>>
}