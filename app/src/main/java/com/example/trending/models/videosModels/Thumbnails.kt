package com.example.trending.models.videosModels

import com.google.gson.annotations.SerializedName



data class Thumbnails (

    @SerializedName("default") val default : Default,
    @SerializedName("medium") val medium : Medium,
    @SerializedName("high") val high : High,
    @SerializedName("standard") val standard : Standard,
    @SerializedName("maxres") val maxres : Maxres
)