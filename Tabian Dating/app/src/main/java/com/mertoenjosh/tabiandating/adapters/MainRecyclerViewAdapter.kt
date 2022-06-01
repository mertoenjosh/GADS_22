package com.mertoenjosh.tabiandating.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mertoenjosh.tabiandating.IMainActivity
import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.models.User


class MainRecyclerViewAdapter(val context: Context, private val users: ArrayList<User>) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>() {
    //vars
    private var mInterface: IMainActivity? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_main_feed, parent, false)
        return ViewHolder(view)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mInterface = context as IMainActivity
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
         val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background) /// ???
        Glide.with(context)
            .load(users[position].profile_image)
            .apply(requestOptions)
            .into(holder.image)
//            .placeholder(R.drawable.ic_launcher_background)
        holder.name.text = users[position].name
        holder.interested_in.text = users[position].interested_in
        holder.status.text = users[position].status
        holder.cardView.setOnClickListener {
            Log.d(TAG, "onClick: clicked on: ")
            mInterface?.inflateViewProfileFragment(users[position])
        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var name: TextView
        var interested_in: TextView
        var status: TextView
        var cardView: CardView

        init {
            image = itemView.findViewById(R.id.profile_image)
            name = itemView.findViewById(R.id.name)
            interested_in = itemView.findViewById(R.id.interested_in)
            status = itemView.findViewById(R.id.status)
            cardView = itemView.findViewById(R.id.card_view)
        }
    }

    companion object {
        private const val TAG = "MainRecyclerViewAd"
    }
}

