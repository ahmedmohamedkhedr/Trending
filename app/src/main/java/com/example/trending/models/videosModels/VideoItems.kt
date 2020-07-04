package com.example.trending.models.videosModels

import com.google.gson.annotations.SerializedName


data class VideoItems(

    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("id") val id: String,
    @SerializedName("snippet") val snippet: Snippet
)