package com.example.trending.models.responses

import com.example.trending.models.categoryModels.Items
import com.google.gson.annotations.SerializedName



data class CategoriesResponse (

	@SerializedName("kind") val kind : String,
	@SerializedName("etag") val etag : String,
	@SerializedName("items") val items : List<Items>
)