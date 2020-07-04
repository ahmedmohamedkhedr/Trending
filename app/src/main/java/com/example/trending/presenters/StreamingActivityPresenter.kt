package com.example.trending.presenters

import com.example.trending.caching.savesVideos.VideoCachingDatabase
import com.example.trending.models.responses.ChannelResponse
import com.example.trending.network.Api
import com.example.trending.ui.views.SavesFragmentView
import com.example.trending.ui.views.StreamingActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class StreamingActivityPresenter(
    private val streamView: StreamingActivityView,
    private val api: Api,
    private val savesView: SavesFragmentView,
    private val db: VideoCachingDatabase
) : SavesFragmentPresenter(savesView, db) {

    // get channel from api
    fun getChannel(id: String, disposable: CompositeDisposable) {
        disposable.add(
            api.getChannel(id).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ChannelResponse>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: ChannelResponse) {
                        streamView.onReceiveChannel(
                            t.items[t.items.size - 1].snippet.title,
                            t.items[t.items.size - 1].snippet.thumbnails.default.url,
                            null
                        )
                    }

                    override fun onError(e: Throwable) {
                        streamView.onReceiveChannel(null, null, e.message)
                    }

                })
        )
    }


}