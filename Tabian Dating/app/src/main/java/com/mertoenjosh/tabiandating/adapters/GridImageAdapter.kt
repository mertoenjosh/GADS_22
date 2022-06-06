package com.mertoenjosh.tabiandating.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.settings.SquareImageView

class GridImageAdapter(context: Context, private val layoutResource: Int, imgURLs: ArrayList<String>) :
    ArrayAdapter<String?>(context, layoutResource, imgURLs as List<String?>) {
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        var image: SquareImageView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        /*
        Viewholder build pattern (Similar to recyclerview)
         */
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false)
            holder = ViewHolder()
            holder.image = convertView.findViewById<View>(R.id.gridImageView) as SquareImageView
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        Glide.with(context)
            .load(getItem(position))
            .into(holder.image as ImageView)
        return convertView!!
    }
}