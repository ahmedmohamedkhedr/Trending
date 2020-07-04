package com.example.trending.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.trending.R
import com.example.trending.caching.cachingCategories.CategoryItem
import com.example.trending.ui.Id
import com.example.trending.ui.Layout

class CategoriesAdapter(
    context: Context,
    private val list: ArrayList<CategoryItem>
) :
    ArrayAdapter<CategoryItem>(context, Layout.category_item, list) {


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = LayoutInflater.from(context).inflate(Layout.category_item, parent, false)
        val kind = view.findViewById<TextView>(Id.categoryKind)
        val category = getItem(position)
        if (category != null) {
            kind.text = list[position].title
        }
        return view
    }

}