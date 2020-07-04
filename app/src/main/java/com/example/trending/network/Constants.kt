package com.example.trending.network

object Constants {

    const val API_KEY = "AIzaSyACgoOhhwxqFWHhtlfJiYA2HTgXeezAtbE"
    private const val MAX_RESULTS = "&maxResults=100"
    const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    const val VIDEOS_ENDPOINT = "videos?part=snippet&chart=mostPopular&regionCode=GB$MAX_RESULTS&key=$API_KEY"
    const val SEARCH_ENDPOINT = "search?part=snippet&maxResults=25&key=$API_KEY"
    const val CATEGORIES_ENDPOINT = "videoCategories?part=snippet&regionCode=GB&key=$API_KEY"
    const val CHANNELS_ENDPOINT = "channels?part=snippet&key=$API_KEY"

}