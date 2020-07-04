package com.example.trending.caching.cachingCategories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "item_title") val title: String,
    @ColumnInfo(name = "item_id") val itemId: Int
)
