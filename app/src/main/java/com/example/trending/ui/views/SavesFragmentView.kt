package com.example.trending.ui.views

import com.example.trending.caching.savesVideos.VideoCachingItem

interface SavesFragmentView {

    fun onSaveVideo(message: String?)
    fun onReceiveAllSaves(list: MutableList<VideoCachingItem>)
    fun onReceiveCurrentVideo(video: VideoCachingItem?)
    fun onRemoveItemFromSaves(message: String?)

}