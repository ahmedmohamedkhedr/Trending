package com.example.trending.models.passingModel

import java.io.Serializable


data class Video(
    val videoId: String,
    val description: String?,
    val title: String?,
    val channelId: String,
    val poster: String

) : Serializable