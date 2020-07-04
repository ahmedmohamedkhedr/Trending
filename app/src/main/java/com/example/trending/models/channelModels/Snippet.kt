package com.example.trending.models.channelModels

import com.google.gson.annotations.SerializedName


data class Snippet (

	@SerializedName("title") val title : String,
	@SerializedName("description") val description : String,
	@SerializedName("customUrl") val customUrl : String,
	@SerializedName("publishedAt") val publishedAt : String,
	@SerializedName("thumbnails") val thumbnails : Thumbnails,
	@SerializedName("localized") val localized : Localized
)