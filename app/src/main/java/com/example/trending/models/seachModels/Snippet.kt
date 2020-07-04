package com.example.trending.models.seachModels

import com.google.gson.annotations.SerializedName


data class Snippet (

	@SerializedName("publishedAt") val publishedAt : String,
	@SerializedName("channelId") val channelId : String,
	@SerializedName("title") val title : String,
	@SerializedName("description") val description : String,
	@SerializedName("thumbnails") val thumbnails : Thumbnails,
	@SerializedName("channelTitle") val channelTitle : String,
	@SerializedName("liveBroadcastContent") val liveBroadcastContent : String,
	@SerializedName("publishTime") val publishTime : String
)