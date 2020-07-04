package com.example.trending.presenters

import com.example.trending.models.responses.SearchResponse
import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.example.trending.models.seachModels.SearchItems
import com.example.trending.network.Api
import com.example.trending.ui.views.SearchFragmentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragmentPresenter(private val view: SearchFragmentView, private val api: Api) {
    // observe on edit text and pass input to api
    @SuppressLint("CheckResult")
    fun searchAbout(searchInput: EditText, disposable: CompositeDisposable) {
        Observable.create<String> {
            searchInput.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!TextUtils.isEmpty(s)) {
                        view.setProgressVisibility(View.VISIBLE)
                        it.onNext(s.toString())

                    } else {
                        view.setProgressVisibility(View.GONE)
                    }
                }

            })

        }.debounce(5000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe {
                observeOnText(it, disposable)
            }
    }

    @SuppressLint("CheckResult")
    private fun observeOnText(q: String, disposable: CompositeDisposable) {
        disposable.add(
            api.searchingAbout(q)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<SearchResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: SearchResponse) {
                        view.onReceiveSearchResults(t.items, null)
                    }

                    override fun onError(e: Throwable) {
                        view.onReceiveSearchResults(null, e.message)
                    }

                })
        )

    }

    //filter result from api to return videos only
    fun filterSearchResults(list: List<SearchItems>): MutableList<SearchItems> {
        val mutableList: MutableList<SearchItems> = ArrayList()
        for (item in list) {
            item.id.videoId?.let {
                mutableList.add(item)
            }
        }
        return mutableList
    }
}