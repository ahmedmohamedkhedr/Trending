package com.example.trending.caching.savesVideos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideoCachingDAO {
    @Insert
    fun save(item: VideoCachingItem)

    @Query("SELECT * FROM VideoCachingItem")
    fun getAllSaves(): MutableList<VideoCachingItem>

    @Query("SELECT * FROM VideoCachingItem WHERE video_id LIKE :videoId")
    fun getByVideoId(videoId: String): VideoCachingItem?

    @Delete
    fun unSave(item: VideoCachingItem)

}