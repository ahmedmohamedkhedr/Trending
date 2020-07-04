package com.example.trending.models.responses

import com.example.trending.models.seachModels.PageInfo
import com.example.trending.models.seachModels.SearchItems
import com.google.gson.annotations.SerializedName


data class SearchResponse(

    @SerializedName("kind") val kind : String,
    @SerializedName("etag") val etag : String,
    @SerializedName("nextPageToken") val nextPageToken : String,
    @SerializedName("regionCode") val regionCode : String,
    @SerializedName("pageInfo") val pageInfo : PageInfo,
    @SerializedName("items") val items : List<SearchItems>
)