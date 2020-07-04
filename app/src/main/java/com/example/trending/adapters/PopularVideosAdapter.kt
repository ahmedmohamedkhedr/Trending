package com.example.trending.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trending.R
import com.example.trending.models.videosModels.VideoItems
import com.example.trending.ui.Id
import com.example.trending.ui.Layout
import com.squareup.picasso.Picasso

class PopularVideosAdapter(
    val list: List<VideoItems>,
    val listener: OnVideoClickListener<VideoItems>
) :
    RecyclerView.Adapter<PopularVideosAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(Layout.viedo_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.publisher?.text = list[position].snippet.title
        holder.date?.text = list[position].snippet.publishedAt
        Picasso.get().load(list[position].snippet.thumbnails.medium.url).into(holder.videoPoster)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var publisher: TextView? = null
        var date: TextView? = null
        var videoPoster: ImageView? = null
        private var playBtn: ImageView? = null

        init {
            publisher = itemView.findViewById(Id.publisher)
            date = itemView.findViewById(Id.date)
            videoPoster = itemView.findViewById(Id.video_poster)
            playBtn = itemView.findViewById(Id.play_btn)
            playBtn?.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(list[adapterPosition])
                }
            }
        }
    }
}