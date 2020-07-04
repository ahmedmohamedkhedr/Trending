package com.example.trending.network

import com.example.trending.models.responses.ChannelResponse
import com.example.trending.models.responses.VideoResponse
import com.example.trending.models.responses.CategoriesResponse
import com.example.trending.models.responses.SearchResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET(Constants.CATEGORIES_ENDPOINT)
    fun getCategories(): Call<CategoriesResponse>

    @GET(Constants.VIDEOS_ENDPOINT)
    fun getPopularVideos(): Observable<VideoResponse>

    @GET(Constants.VIDEOS_ENDPOINT)
    fun getVideosByCategoryId(@Query("videoCategoryId") categoryId: Int): Observable<VideoResponse>

    @GET(Constants.SEARCH_ENDPOINT)
    fun searchingAbout(@Query("q") q: String): Observable<SearchResponse>

    @GET(Constants.CHANNELS_ENDPOINT)
    fun getChannel(@Query("id") id: String): Observable<ChannelResponse>

}