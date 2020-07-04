package com.example.trending.caching.savesVideos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class VideoCachingItem(
     var id: String,
     var title: String?,
     var desc: String?,
     var idChannel: String,
     var posterUri: String
) {
    @PrimaryKey(autoGenerate = true)
     var primaryKey: Int = 0

    @ColumnInfo(name = "video_id")
     var videoId: String = id

    @ColumnInfo(name = "video_title")
     var videoTitle: String? = title

    @ColumnInfo(name = "video_description")
     var videoDescription: String? = desc

    @ColumnInfo(name = "channel_id")
     var channelId: String = idChannel

    @ColumnInfo(name = "video_poster")
     var poster: String = posterUri


}