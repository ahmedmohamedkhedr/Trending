package com.example.trending.ui.views

import com.example.trending.models.seachModels.SearchItems

interface SearchFragmentView {

    fun onReceiveSearchResults(list: List<SearchItems>? , err:String?)
    fun setProgressVisibility(visibility:Int)

}