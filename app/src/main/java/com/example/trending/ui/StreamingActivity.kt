package com.example.trending.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.trending.R
import com.example.trending.caching.savesVideos.VideoCachingDatabase
import com.example.trending.caching.savesVideos.VideoCachingItem
import com.example.trending.models.passingModel.Video
import com.example.trending.network.Constants
import com.example.trending.presenters.StreamingActivityPresenter
import com.example.trending.ui.views.SavesFragmentView
import com.example.trending.ui.views.StreamingActivityView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class StreamingActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener,
    StreamingActivityView, SavesFragmentView {
    private lateinit var streamContainer: LinearLayout
    private lateinit var streamProgress: ProgressBar
    private lateinit var channelTitleTextView: TextView
    private lateinit var channelLogo: CircleImageView
    private lateinit var player: YouTubePlayerView
    private lateinit var description: TextView
    private lateinit var saveBtn: ImageView
    private lateinit var videoId: String
    private lateinit var video: Video
    private val presenter: StreamingActivityPresenter by inject { parametersOf(this, this) }
    private val disposable: CompositeDisposable by inject()
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(Layout.activity_streaming)
        player = findViewById(Id.youtubePlayer)
        description = findViewById(Id.video_description)
        streamContainer = findViewById(Id.streamContainer)
        streamProgress = findViewById(Id.streamProgress)
        channelTitleTextView = findViewById(Id.channelTitle)
        channelLogo = findViewById(Id.channelLogo)
        saveBtn = findViewById(Id.saveBtn)
        video = intent.getSerializableExtra(getString(R.string.video)) as Video
        videoId = video.videoId
        presenter.getChannel(video.channelId, disposable)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }


    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.loadVideo(videoId)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Toast.makeText(this, p1?.name, Toast.LENGTH_LONG).show()
    }

    // when receive channel logo and title from api
    override fun onReceiveChannel(channelTitle: String?, channelLogo: String?, err: String?) {
        if (err == null) {
            this.channelTitleTextView.text = channelTitle
            Picasso.get().load(channelLogo).placeholder(Drawable.user).into(this.channelLogo)
            video.description?.let {
                this.description.text = it
            }
            this.streamContainer.visibility = View.VISIBLE
            this.player.initialize(Constants.API_KEY, this)
            presenter.getByVideoId(videoId)
        } else {
            Toast.makeText(this, err, Toast.LENGTH_LONG).show()
            finish()
        }
        this.streamProgress.visibility = View.GONE
    }

    //when user click on save button
    override fun onSaveVideo(message: String?) {
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            isSaved = true
            saveBtn.setImageResource(Drawable.saved)
        }

    }

    //we won't use this method here
    override fun onReceiveAllSaves(list: MutableList<VideoCachingItem>) {

    }

    //check if current video is found on saves videos or not
    override fun onReceiveCurrentVideo(video: VideoCachingItem?) {
        if (video != null) {
            isSaved = true
            saveBtn.setImageResource(Drawable.saved)
        }
    }

    //when user want to un save this video
    override fun onRemoveItemFromSaves(message: String?) {
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            isSaved = false
            saveBtn.setImageResource(Drawable.save)

        }

    }


    //when user click on save button
    fun onClickSave(view: View) {
        if (!isSaved) {
            val item: VideoCachingItem by inject {
                parametersOf(
                    videoId,
                    video.title,
                    video.description,
                    video.channelId,
                    video.poster
                )
            }
            presenter.save(item)
        } else {
            presenter.unSave(videoId)
        }

    }


}