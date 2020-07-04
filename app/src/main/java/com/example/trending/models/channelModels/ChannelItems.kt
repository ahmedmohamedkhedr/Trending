package com.example.trending.models.channelModels

import com.google.gson.annotations.SerializedName


data class ChannelItems (

	@SerializedName("kind") val kind : String,
	@SerializedName("etag") val etag : String,
	@SerializedName("id") val id : String,
	@SerializedName("snippet") val snippet : Snippet
)