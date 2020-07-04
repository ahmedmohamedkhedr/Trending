package com.example.trending.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trending.R
import com.example.trending.adapters.OnVideoClickListener
import com.example.trending.adapters.SearchListAdapter
import com.example.trending.models.passingModel.Video
import com.example.trending.models.seachModels.SearchItems
import com.example.trending.presenters.SearchFragmentPresenter
import com.example.trending.ui.views.SearchFragmentView
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SearchFragment : Fragment(), SearchFragmentView, OnVideoClickListener<SearchItems> {
    private lateinit var searchInput: EditText
    private lateinit var searchProgressBar: ProgressBar
    private lateinit var searchList: RecyclerView
    private val presenter: SearchFragmentPresenter by inject { parametersOf(this) }
    private val disposable: CompositeDisposable by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(Layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchInput = view.findViewById(Id.searchInput)
        searchProgressBar = view.findViewById(Id.searchProgress)
        searchList = view.findViewById(Id.searchList)
        searchList.layoutManager = LinearLayoutManager(context)
        presenter.searchAbout(searchInput, disposable)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    // receive search list results from api and pass it to search adapter
    override fun onReceiveSearchResults(list: List<SearchItems>?, err: String?) {
        list?.let {
            val finalResults = presenter.filterSearchResults(it)
            val adapter: SearchListAdapter by inject { parametersOf(finalResults, this) }
            searchList.adapter = adapter
            searchProgressBar.visibility = View.GONE
            return
        }
        Toast.makeText(context, err, Toast.LENGTH_LONG).show()
        searchProgressBar.visibility = View.GONE
    }

    //set progress bar visibility
    override fun setProgressVisibility(visibility: Int) {
        searchProgressBar.visibility = visibility
    }


    // when user click on specific item
    override fun onItemClicked(obj: SearchItems) {

        val video: Video by inject {
            parametersOf(
                obj.id.videoId,
                obj.snippet.description,
                obj.snippet.title,
                obj.snippet.channelId,
                obj.snippet.thumbnails.default.url
            )
        }
        val streamIntent = Intent(requireContext(), StreamingActivity::class.java)
        streamIntent.putExtra(getString(R.string.video), video)
        startActivity(streamIntent)

    }


}