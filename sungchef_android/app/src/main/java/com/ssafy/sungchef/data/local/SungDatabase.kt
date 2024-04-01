package com.ssafy.sungchef.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.sungchef.data.local.dao.BookmarkDao
import com.ssafy.sungchef.data.model.entity.BookmarkEntity

@Database(
    entities = [BookmarkEntity::class],
    version = 1,
    exportSchema = true
)
abstract class SungDatabase:RoomDatabase() {
    abstract val bookmarkDao:BookmarkDao
}