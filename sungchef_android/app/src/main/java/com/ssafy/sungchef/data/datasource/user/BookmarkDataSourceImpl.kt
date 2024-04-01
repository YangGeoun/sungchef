package com.ssafy.sungchef.data.datasource.user

import com.ssafy.sungchef.data.local.dao.BookmarkDao
import com.ssafy.sungchef.data.model.entity.BookmarkEntity
import javax.inject.Inject

class BookmarkDataSourceImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
):BookmarkDataSource {
    override suspend fun insert(bookmarkEntity: BookmarkEntity) {
        bookmarkDao.insert(bookmarkEntity)
    }

    override suspend fun delete(bookmarkEntity: BookmarkEntity) {
        bookmarkDao.delete(bookmarkEntity)
    }

    override suspend fun selectBookmark(id: Int): BookmarkEntity? {
        return bookmarkDao.selectRecipeId(id)
    }
}