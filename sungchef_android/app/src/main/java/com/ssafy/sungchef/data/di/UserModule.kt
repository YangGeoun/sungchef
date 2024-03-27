package com.ssafy.sungchef.data.di

import com.ssafy.sungchef.data.datasource.user.UserDataSource
import com.ssafy.sungchef.data.datasource.user.UserDataSourceImpl
import com.ssafy.sungchef.data.repository.UserDataStoreRepositoryImpl
import com.ssafy.sungchef.data.repository.UserRepositoryImpl
import com.ssafy.sungchef.domain.repository.UserDataStoreRepository
import com.ssafy.sungchef.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserDataSource(userDateSourceImpl: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository

    @Binds
    @Singleton
    abstract fun bindUserDataStoreRepository(userDataStoreRepositoryImpl: UserDataStoreRepositoryImpl) : UserDataStoreRepository
}