package com.example.trending.ui.views

import com.example.trending.caching.cachingCategories.CategoryItem
import com.example.trending.models.videosModels.VideoItems
import com.example.trending.models.categoryModels.Items

interface HomeFragmentView {

    fun onReceiveCategories(list: ArrayList<Items>?)
    fun onReceiveCategoryById(item:CategoryItem?)
    fun onReceiveCachingCategories(list: ArrayList<CategoryItem>)
    fun onReceivePopularVideos(list: List<VideoItems>? , err: String?)
    fun onReceiveVideosByCategoryId(list: List<VideoItems>? , err: String?)

}