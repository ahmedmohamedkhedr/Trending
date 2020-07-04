package com.example.trending.caching.cachingCategories

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryItem::class], version = 1)
abstract class CategoryCachingDatabase : RoomDatabase() {

    abstract fun getCachingCategoriesDao(): CachingCategoriesDAO

}