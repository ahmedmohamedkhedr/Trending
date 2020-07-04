package com.example.trending.models.responses

import com.example.trending.models.channelModels.ChannelItems
import com.example.trending.models.channelModels.PageInfo
import com.google.gson.annotations.SerializedName



data class ChannelResponse(

	@SerializedName("kind") val kind : String,
	@SerializedName("etag") val etag : String,
	@SerializedName("pageInfo") val pageInfo : PageInfo,
	@SerializedName("items") val items : List<ChannelItems>
)