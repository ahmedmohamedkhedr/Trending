package com.example.trending.models.categoryModels

import com.google.gson.annotations.SerializedName



data class Snippet (

	@SerializedName("title") val title : String,
	@SerializedName("assignable") val assignable : Boolean,
	@SerializedName("channelId") val channelId : String
)