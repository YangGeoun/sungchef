package com.ssafy.sungchef.data.di

import android.app.Application
import androidx.room.Room
import com.ssafy.sungchef.data.datasource.user.BookmarkDataSource
import com.ssafy.sungchef.data.datasource.user.BookmarkDataSourceImpl
import com.ssafy.sungchef.data.local.SungDatabase
import com.ssafy.sungchef.data.model.entity.tableName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideSungDatabase(app:Application):SungDatabase{
        return Room.databaseBuilder(app, SungDatabase::class.java, tableName).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDataSource(myDB: SungDatabase): BookmarkDataSource =
        BookmarkDataSourceImpl(myDB.bookmarkDao)
}