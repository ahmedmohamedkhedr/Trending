package com.example.trending.models.categoryModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Items(
    @PrimaryKey(autoGenerate = true) val primaryKey: Int = 0,
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("id") val id: Int,
    @SerializedName("snippet") val snippet: Snippet
)