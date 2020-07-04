package com.example.trending.modules

import android.content.Context
import androidx.room.Room
import com.example.trending.R
import com.example.trending.adapters.*
import com.example.trending.caching.cachingCategories.CategoryCachingDatabase
import com.example.trending.caching.cachingCategories.CategoryItem
import com.example.trending.caching.savesVideos.VideoCachingDatabase
import com.example.trending.caching.savesVideos.VideoCachingItem
import com.example.trending.models.passingModel.Video
import com.example.trending.models.seachModels.SearchItems
import com.example.trending.models.videosModels.VideoItems
import com.example.trending.network.Api
import com.example.trending.network.Constants
import com.example.trending.presenters.HomeFragmentPresenter
import com.example.trending.presenters.SavesFragmentPresenter
import com.example.trending.presenters.SearchFragmentPresenter
import com.example.trending.presenters.StreamingActivityPresenter
import com.example.trending.ui.views.HomeFragmentView
import com.example.trending.ui.views.SavesFragmentView
import com.example.trending.ui.views.SearchFragmentView
import com.example.trending.ui.views.StreamingActivityView
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


//all application modules
val appModules = module {
    //adapters
    single { (list: ArrayList<CategoryItem>) ->
        CategoriesAdapter(androidContext(), list)
    }

    factory { (list: List<VideoItems>, listener: OnVideoClickListener<VideoItems>) ->
        PopularVideosAdapter(list, listener)
    }

    factory { (list: MutableList<SearchItems>, listener: OnVideoClickListener<SearchItems>) ->
        SearchListAdapter(list, listener)
    }

    factory { (list: MutableList<VideoCachingItem>,
                  listener: OnVideoClickListener<VideoCachingItem>,
                  deleteListener: SavesAdapter.OnDeleteListener) ->
        SavesAdapter(list, listener , deleteListener)
    }

    //disposable
    single {
        getCompositeDisposable()
    }

    //presenters
    factory { (view: SearchFragmentView) ->
        SearchFragmentPresenter(view, get())
    }

    factory { (view: HomeFragmentView) ->
        HomeFragmentPresenter(view, get())
    }
    factory { (streamView: StreamingActivityView, savesView: SavesFragmentView) ->
        StreamingActivityPresenter(streamView, get(), savesView, get())
    }
    factory { (savesView: SavesFragmentView) ->
        SavesFragmentPresenter(savesView, get())
    }
    //retrofit
    single {
        getRetrofit()
    }

    //api
    single {
        getApi(get())
    }

    //room
    single {
        getCategoryCachingDatabase(androidContext())
    }
    single {
        getVideoCachingDatabase(androidContext())
    }

    //video model
    factory { (videoId: String, description: String?, title: String?, channelId: String, poster: String) ->
        Video(videoId, description, title, channelId, poster)
    }

    // video caching item
    factory { (id: String,
                  title: String?,
                  desc: String?,
                  idChannel: String,
                  posterUri: String) ->
        VideoCachingItem(id, title, desc, idChannel, posterUri)
    }

}


// this for return composite disposable
fun getCompositeDisposable(): CompositeDisposable {
    return CompositeDisposable()
}

//this method return retrofit
fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

//this method return api interface
fun getApi(client: Retrofit): Api {
    return getRetrofit().create(Api::class.java)
}


//this return our database class
fun getCategoryCachingDatabase(context: Context): CategoryCachingDatabase {
    return Room.databaseBuilder(
        context,
        CategoryCachingDatabase::class.java,
        context.getString(R.string.categories_database)
    ).build()
}

fun getVideoCachingDatabase(context: Context): VideoCachingDatabase {
    return Room.databaseBuilder(
        context,
        VideoCachingDatabase::class.java,
        context.getString(R.string.videos_database)
    ).build()

}

