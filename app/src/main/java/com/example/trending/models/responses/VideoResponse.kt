package com.example.trending.models.responses

import com.example.trending.models.videosModels.PageInfo
import com.example.trending.models.videosModels.VideoItems
import com.google.gson.annotations.SerializedName



data class VideoResponse(

    @SerializedName("kind") val kind : String,
    @SerializedName("etag") val etag : String,
    @SerializedName("items") val items : List<VideoItems>,
    @SerializedName("nextPageToken") val nextPageToken : String,
    @SerializedName("pageInfo") val pageInfo : PageInfo
)