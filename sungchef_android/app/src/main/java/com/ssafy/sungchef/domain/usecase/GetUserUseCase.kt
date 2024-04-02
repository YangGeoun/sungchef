package com.ssafy.sungchef.domain.usecase

import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.domain.model.recommendation.UserInfo
import com.ssafy.sungchef.domain.repository.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val recommendationRepository: RecommendationRepository
) {
    suspend operator fun invoke(): Flow<DataState<UserInfo>> = recommendationRepository.getUser()
}