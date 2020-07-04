package com.example.trending.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trending.R
import com.example.trending.adapters.CategoriesAdapter
import com.example.trending.adapters.OnVideoClickListener
import com.example.trending.adapters.PopularVideosAdapter
import com.example.trending.caching.cachingCategories.CategoryCachingDatabase
import com.example.trending.caching.cachingCategories.CategoryItem
import com.example.trending.models.videosModels.VideoItems
import com.example.trending.models.categoryModels.Items
import com.example.trending.models.passingModel.Video
import com.example.trending.presenters.HomeFragmentPresenter
import com.example.trending.ui.views.HomeFragmentView
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(), HomeFragmentView, AdapterView.OnItemClickListener,
    OnVideoClickListener<VideoItems> {
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var mostPopularList: RecyclerView
    private lateinit var categories: ListView
    private lateinit var homeProgressBar: ProgressBar
    private lateinit var categoryProgressBar: ProgressBar
    private val presenter: HomeFragmentPresenter by inject { parametersOf(this) }
    private val db: CategoryCachingDatabase by inject()
    private val disposable: CompositeDisposable by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(Layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(Id.supportToolbar)
        drawer = view.findViewById(Id.drawer)
        categories = view.findViewById(Id.categories)
        homeProgressBar = view.findViewById(Id.homeProgress)
        mostPopularList = view.findViewById(Id.trendingList)
        categoryProgressBar = view.findViewById(Id.categoryProgress)
        mostPopularList.layoutManager = LinearLayoutManager(context)
        categories.onItemClickListener = this
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity.supportActionBar?.setHomeButtonEnabled(true)
        setUpDrawerToggle()
        drawer.addDrawerListener(actionBarDrawerToggle)
        presenter.getCachingCategories(db)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    // receive categories from api then caching it
    override fun onReceiveCategories(list: ArrayList<Items>?) {
        list?.let {
            presenter.cachingCategories(it, db)
        }

    }

    //when user click on specific category to get category id
    override fun onReceiveCategoryById(item: CategoryItem?) {

        item?.let {
            categoryProgressBar.visibility = View.VISIBLE
            presenter.getVideosByCategoryId(it.itemId, disposable)
        }
    }

    // display categories within side menu
    override fun onReceiveCachingCategories(list: ArrayList<CategoryItem>) {
        if (list.isEmpty()) {
            presenter.getCategories()
        } else {
            val adapter: CategoriesAdapter by inject { parametersOf(list) }
            categories.adapter = adapter
            presenter.getPopularVideos(disposable)
        }
    }

    //receive popular videos from api and display it within most popular list
    override fun onReceivePopularVideos(list: List<VideoItems>?, err: String?) {
        list?.let {
            val adapter: PopularVideosAdapter by inject { parametersOf(it, this) }
            mostPopularList.adapter = adapter
            homeProgressBar.visibility = View.GONE
            return
        }
        Toast.makeText(context, err, Toast.LENGTH_LONG).show()
        homeProgressBar.visibility = View.GONE

    }

    //receive videos by category and display it
    override fun onReceiveVideosByCategoryId(list: List<VideoItems>?, err: String?) {
        list?.let {
            val adapter: PopularVideosAdapter by inject { parametersOf(it, this) }
            mostPopularList.adapter = adapter
            categoryProgressBar.visibility = View.GONE
            return
        }
        Toast.makeText(context, err, Toast.LENGTH_LONG).show()
        categoryProgressBar.visibility = View.GONE
    }

    //category list adapter listener
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        drawer.closeDrawer(GravityCompat.START)
        presenter.getCategoryById(position + 1, db)
    }

    //setup side menu
    private fun setUpDrawerToggle() {
        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawer,
            toolbar,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
    }

    //when user click on play btn to specific video
    override fun onItemClicked(obj: VideoItems) {

        val streamIntent = Intent(requireContext(), StreamingActivity::class.java)
        val video: Video by inject {
            parametersOf(
                obj.id,
                obj.snippet.description,
                obj.snippet.title,
                obj.snippet.channelId,
                obj.snippet.thumbnails.default.url
            )
        }
        streamIntent.putExtra(getString(R.string.video), video)
        startActivity(streamIntent)

    }


}