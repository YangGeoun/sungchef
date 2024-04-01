package com.ssafy.sungchef.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val tableName = "BookmarkEntity"

@Entity(tableName = tableName)
data class BookmarkEntity(
    @PrimaryKey
    val recipeId: Int = 0
)
