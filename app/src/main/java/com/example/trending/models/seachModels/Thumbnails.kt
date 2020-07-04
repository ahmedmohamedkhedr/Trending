package com.example.trending.models.seachModels

import com.google.gson.annotations.SerializedName



data class Thumbnails (

    @SerializedName("default") val default : Default,
    @SerializedName("medium") val medium : Medium,
    @SerializedName("high") val high : High
)