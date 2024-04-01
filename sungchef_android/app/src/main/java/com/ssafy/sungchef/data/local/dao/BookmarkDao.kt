package com.ssafy.sungchef.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.sungchef.data.model.entity.BookmarkEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmarkEntity: BookmarkEntity)
    @Delete
    suspend fun delete(bookmarkEntity: BookmarkEntity)
    @Query("SELECT * FROM BookmarkEntity WHERE recipeId= :recipeId")
    suspend fun selectRecipeId(recipeId: Int): BookmarkEntity?

}