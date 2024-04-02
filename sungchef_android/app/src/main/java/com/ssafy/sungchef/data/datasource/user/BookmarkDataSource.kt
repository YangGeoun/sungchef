package com.ssafy.sungchef.data.datasource.user

import com.ssafy.sungchef.data.model.entity.BookmarkEntity

interface BookmarkDataSource {
    suspend fun insert(bookmarkEntity: BookmarkEntity)
    suspend fun delete(bookmarkEntity: BookmarkEntity)
    suspend fun selectBookmark(id:Int): BookmarkEntity?
}