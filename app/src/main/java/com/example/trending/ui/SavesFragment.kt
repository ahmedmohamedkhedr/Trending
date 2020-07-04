package com.example.trending.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trending.R
import com.example.trending.adapters.OnVideoClickListener
import com.example.trending.adapters.SavesAdapter
import com.example.trending.caching.savesVideos.VideoCachingItem
import com.example.trending.models.passingModel.Video
import com.example.trending.presenters.SavesFragmentPresenter
import com.example.trending.ui.views.SavesFragmentView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SavesFragment : Fragment(), SavesFragmentView, OnVideoClickListener<VideoCachingItem>,
    SavesAdapter.OnDeleteListener {
    private lateinit var toolbar: Toolbar
    private lateinit var savesList: RecyclerView
    private lateinit var savesProgressBar: ProgressBar
    private val presenter: SavesFragmentPresenter by inject { parametersOf(this, this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(Layout.fragment_saves, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(Id.savesToolbar)
        savesList = view.findViewById(Id.savesList)
        savesProgressBar = view.findViewById(Id.savesProgress)
        toolbar.title = resources.getString(R.string.saves)
        savesList.layoutManager = GridLayoutManager(requireContext(), 3)

    }

    override fun onStart() {
        super.onStart()
        presenter.getAllSaves()
    }

    //we won't use this method here
    override fun onSaveVideo(message: String?) {

    }

    override fun onReceiveAllSaves(list: MutableList<VideoCachingItem>) {

        if (list.isEmpty()) {
            Toast.makeText(context, "There's no saves videos", Toast.LENGTH_LONG)
                .show()
        }
        val adapter: SavesAdapter by inject { parametersOf(list, this, this) }
        savesList.adapter = adapter
        savesProgressBar.visibility = View.GONE
    }

    //we won't use this method here
    override fun onReceiveCurrentVideo(video: VideoCachingItem?) {

    }

    override fun onRemoveItemFromSaves(message: String?) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClicked(obj: VideoCachingItem) {
        val video: Video by inject {
            parametersOf(
                obj.videoId,
                obj.videoDescription,
                obj.videoTitle,
                obj.channelId,
                obj.poster
            )
        }
        val streamIntent = Intent(context, StreamingActivity::class.java)
        streamIntent.putExtra(getString(R.string.video), video)
        startActivity(streamIntent)

    }

    // when user click on delete button
    override fun onClickDelete(itemId: String) {
        presenter.unSave(itemId)
    }


}