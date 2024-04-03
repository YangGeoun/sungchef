package com.ssafy.sungchef.data.repository

import com.ssafy.sungchef.data.datasource.recommendation.RecommendationDataSource
import com.ssafy.sungchef.data.mapper.recommendation.toRecommendation
import com.ssafy.sungchef.commons.DataState
import com.ssafy.sungchef.data.mapper.recommendation.toUserInfo
import com.ssafy.sungchef.domain.model.recommendation.Recommendation
import com.ssafy.sungchef.domain.model.recommendation.UserInfo
import com.ssafy.sungchef.domain.repository.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val recommendationDataSource: RecommendationDataSource
) : RecommendationRepository {
    override suspend fun getRecommendation(): Flow<DataState<Recommendation>> =
        flow {
            when(val recommendation = recommendationDataSource.getRecommendation()){
                is DataState.Success->{
                    emit(DataState.Success(recommendation.data.data.toRecommendation()))
                }
                is DataState.Error -> {
                    emit(DataState.Error(recommendation.apiError))
                }
                is DataState.Loading ->{
                    emit(DataState.Loading())
                }
            }
        }.onStart { emit(DataState.Loading()) }

    override suspend fun getUser(): Flow<DataState<UserInfo>> =
        flow {
            when(val data = recommendationDataSource.getUser()){
                is DataState.Success->{
                    emit(DataState.Success(data.data.data.toUserInfo()))
                }
                is DataState.Loading->{
                    emit(DataState.Loading())
                }
                is DataState.Error ->{
                    emit(DataState.Error(data.apiError))
                }
            }
        }.onStart { emit(DataState.Loading()) }


}