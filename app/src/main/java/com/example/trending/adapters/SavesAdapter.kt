package com.example.trending.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trending.R
import com.example.trending.caching.savesVideos.VideoCachingItem
import com.example.trending.ui.Id
import com.example.trending.ui.Layout
import com.squareup.picasso.Picasso

class SavesAdapter(
    val items: MutableList<VideoCachingItem>,
    val listener: OnVideoClickListener<VideoCachingItem>,
    private val deleteListener: OnDeleteListener
) :
    RecyclerView.Adapter<SavesAdapter.ViewHolder>() {

    interface OnDeleteListener {
        fun onClickDelete(itemId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(Layout.save_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.saveItemTitle?.text = items[position].title
        Picasso.get().load(items[position].poster).into(holder.saveItemPoster)
        holder.deleteBtn?.setOnClickListener {
            deleteListener.onClickDelete(items[position].videoId)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            items.removeAt(position)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var saveItemTitle: TextView? = null
        var saveItemPoster: ImageView? = null
        private var playSaveItemBtn: ImageView? = null
        var deleteBtn: ImageView? = null

        init {
            saveItemTitle = itemView.findViewById(Id.saveItemTitle)
            saveItemPoster = itemView.findViewById(Id.saveItemPoster)
            playSaveItemBtn = itemView.findViewById(Id.playSaveItemBtn)
            deleteBtn = itemView.findViewById(Id.deleteBtn)
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(items[adapterPosition])
                }
            }
            playSaveItemBtn?.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(items[adapterPosition])
                }
            }

        }
    }

}