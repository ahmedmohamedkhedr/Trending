package com.example.trending.ui.views

interface StreamingActivityView {

    fun onReceiveChannel(channelTitle: String?, channelLogo: String? , err:String?)
}