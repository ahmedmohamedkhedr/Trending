package com.example.trending.caching.savesVideos

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoCachingItem::class], version = 1)
abstract class VideoCachingDatabase : RoomDatabase() {
    abstract fun getCachingVideosDao(): VideoCachingDAO

}