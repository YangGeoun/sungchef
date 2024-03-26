package com.ssafy.sungchef.data.di

import com.ssafy.sungchef.data.datasource.cooking.CookingDataSource
import com.ssafy.sungchef.data.datasource.cooking.CookingDataSourceImpl
import com.ssafy.sungchef.data.repository.CookingRepositoryImpl
import com.ssafy.sungchef.domain.repository.CookingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CookingModule {

    @Binds
    @Singleton
    abstract fun bindCookingDataSource(cookingDataSourceImpl: CookingDataSourceImpl): CookingDataSource

    @Binds
    @Singleton
    abstract fun bindCookingRepository(cookingRepositoryImpl: CookingRepositoryImpl): CookingRepository
}