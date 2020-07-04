package com.example.trending.models.seachModels

import com.google.gson.annotations.SerializedName



data class SearchItems (

	@SerializedName("kind") val kind : String,
	@SerializedName("etag") val etag : String,
	@SerializedName("id") val id : Id,
	@SerializedName("snippet") val snippet : Snippet
)