package com.ssafy.sungchef.data.di

import com.ssafy.sungchef.data.api.RecommendationService
import com.ssafy.sungchef.data.datasource.recommendation.RecommendationDataSource
import com.ssafy.sungchef.data.datasource.recommendation.RecommendationDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RecommendationModule {

    @Provides
    @Singleton
    fun provideRecommendationService(retrofit: Retrofit): RecommendationService =
        retrofit.create(RecommendationService::class.java)

    @Binds
    @Singleton
    abstract fun bindRecommendationDataSource(recommendationDataSourceImpl: RecommendationDataSourceImpl): RecommendationDataSource
}