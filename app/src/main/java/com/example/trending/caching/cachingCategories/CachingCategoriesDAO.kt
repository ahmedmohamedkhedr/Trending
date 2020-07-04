package com.example.trending.caching.cachingCategories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CachingCategoriesDAO {
    @Insert
    fun cachingCategories(item: CategoryItem)

    @Query("SELECT * from  CategoryItem ")
    fun getCategories(): List<CategoryItem>

    @Query("SELECT * from CategoryItem WHERE id LIKE :id")
    fun getCategoryById(id:Int): CategoryItem

}