package com.example.trending.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trending.R
import com.example.trending.models.seachModels.SearchItems
import com.example.trending.ui.Id
import com.example.trending.ui.Layout
import com.squareup.picasso.Picasso

class SearchListAdapter(
    private val list: MutableList<SearchItems>,
    private val listener: OnVideoClickListener<SearchItems>
) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(Layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title?.text = list[position].snippet.title
        Picasso.get().load(list[position].snippet.thumbnails.default.url).into(holder.poster)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var poster: ImageView? = null
        var title: TextView? = null

        init {
            poster = itemView.findViewById(Id.searchResultPoster)
            title = itemView.findViewById(Id.searchResultTitle)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(list[adapterPosition])
                }
            }

        }

    }

}