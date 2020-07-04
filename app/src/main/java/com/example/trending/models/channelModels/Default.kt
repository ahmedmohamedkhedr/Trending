package com.example.trending.models.channelModels

import com.google.gson.annotations.SerializedName



data class Default (

	@SerializedName("url") val url : String,
	@SerializedName("width") val width : Int,
	@SerializedName("height") val height : Int
)