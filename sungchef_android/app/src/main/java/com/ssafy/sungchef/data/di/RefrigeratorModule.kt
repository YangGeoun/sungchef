package com.ssafy.sungchef.data.di

import com.ssafy.sungchef.data.datasource.recommendation.RecommendationDataSource
import com.ssafy.sungchef.data.datasource.recommendation.RecommendationDataSourceImpl
import com.ssafy.sungchef.data.datasource.refrigerator.RefrigeratorDataSource
import com.ssafy.sungchef.data.datasource.refrigerator.RefrigeratorDataSourceImpl
import com.ssafy.sungchef.data.repository.RefrigeratorRepositoryImpl
import com.ssafy.sungchef.domain.repository.RefrigeratorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RefrigeratorModule {
    @Binds
    @Singleton
    abstract fun bindRefrigeratorDataSource(refrigeratorDataSourceImpl: RefrigeratorDataSourceImpl): RefrigeratorDataSource

    @Binds
    @Singleton
    abstract fun bindRefrigeratorRepository(refrigeratorRepositoryImpl: RefrigeratorRepositoryImpl) : RefrigeratorRepository
}