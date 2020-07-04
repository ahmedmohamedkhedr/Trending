package com.example.trending.presenters

import android.annotation.SuppressLint
import com.example.trending.caching.savesVideos.VideoCachingDatabase
import com.example.trending.caching.savesVideos.VideoCachingItem
import com.example.trending.ui.views.SavesFragmentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

open class SavesFragmentPresenter(
    private val view: SavesFragmentView,
    private val db: VideoCachingDatabase
) {
    //save video into room
    @SuppressLint("CheckResult")
    fun save(video: VideoCachingItem) {
        Observable.create<VideoCachingItem> {
            it.onNext(video)
        }.map {
            db.getCachingVideosDao().save(it)
            "saved"
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onSaveVideo(it)
            }
    }

    @SuppressLint("CheckResult")
    fun getAllSaves() {
        Observable.create<MutableList<VideoCachingItem>> {
            it.onNext(db.getCachingVideosDao().getAllSaves())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onReceiveAllSaves(it)
            }
    }

    @SuppressLint("CheckResult")
    fun getByVideoId(videoId: String) {
        Observable.create<VideoCachingItem?> {
            db.getCachingVideosDao().getByVideoId(videoId)?.let { it1 -> it.onNext(it1) }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onReceiveCurrentVideo(it)
            }
    }

    @SuppressLint("CheckResult")
    fun unSave(videoId: String) {
        Observable.create<VideoCachingItem> {
            db.getCachingVideosDao().getByVideoId(videoId)?.let { it1 -> it.onNext(it1) }
        }.map { t ->
            db.getCachingVideosDao().unSave(t)
            "Video removed from saves"
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onRemoveItemFromSaves(it)
            }
    }
}