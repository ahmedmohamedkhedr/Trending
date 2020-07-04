package com.example.trending.presenters

import android.annotation.SuppressLint
import com.example.trending.caching.cachingCategories.CategoryCachingDatabase
import com.example.trending.caching.cachingCategories.CategoryItem
import com.example.trending.models.responses.VideoResponse
import com.example.trending.models.responses.CategoriesResponse
import com.example.trending.models.categoryModels.Items
import com.example.trending.network.Api
import com.example.trending.ui.views.HomeFragmentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentPresenter(private val view: HomeFragmentView, private val api: Api) {
    // get all categories from api
    fun getCategories() {
        api.getCategories().enqueue(object : Callback<CategoriesResponse> {
            override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                view.onReceiveCategories(null)
            }

            override fun onResponse(
                call: Call<CategoriesResponse>,
                response: Response<CategoriesResponse>
            ) {
                if (response.isSuccessful) {
                    view.onReceiveCategories(response.body()?.items as ArrayList<Items>)
                } else {
                    view.onReceiveCategories(null)
                }
            }

        })
    }

    // get all categories from caching
    @SuppressLint("CheckResult")
    fun getCachingCategories(categoryCachingDatabase: CategoryCachingDatabase) {
        Observable.create<List<CategoryItem>> {
            it.onNext(categoryCachingDatabase.getCachingCategoriesDao().getCategories())
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

                view.onReceiveCachingCategories(it as ArrayList<CategoryItem>)
            }
    }

    // get specific category from caching
    @SuppressLint("CheckResult")
    fun getCategoryById(id: Int, db: CategoryCachingDatabase) {
        Observable.create<CategoryItem> {
            it.onNext(db.getCachingCategoriesDao().getCategoryById(id))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onReceiveCategoryById(it)
            }
    }

    // caching all categories into room database
    @SuppressLint("CheckResult")
    fun cachingCategories(list: ArrayList<Items>, db: CategoryCachingDatabase) {
        Observable.create<CategoryItem> {
            for (item in list) {
                val categories = CategoryItem(0, item.snippet.title, item.id)
                it.onNext(categories)
            }
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ item -> db.getCachingCategoriesDao().cachingCategories(item) },
                {},
                { getCachingCategories(db) })
    }

    // get trending videos from api
    fun getPopularVideos(disposable: CompositeDisposable) {
        disposable.add(
            api.getPopularVideos()
                .subscribeOn(
                    Schedulers.computation()
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<VideoResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: VideoResponse) {
                        view.onReceivePopularVideos(t.items, null)
                    }

                    override fun onError(e: Throwable) {
                        view.onReceivePopularVideos(null, e.message)
                    }

                })
        )
    }

    // get trending videos by category id from api
    fun getVideosByCategoryId(id: Int, disposable: CompositeDisposable) {
        disposable.add(
            api.getVideosByCategoryId(id)
                .subscribeOn(
                    Schedulers.computation()
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<VideoResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: VideoResponse) {
                        view.onReceiveVideosByCategoryId(t.items, null)
                    }

                    override fun onError(e: Throwable) {
                        view.onReceiveVideosByCategoryId(null, e.localizedMessage)
                    }

                })
        )
    }
}