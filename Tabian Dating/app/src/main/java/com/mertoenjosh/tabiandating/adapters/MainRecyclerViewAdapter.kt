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


class MainRecyclerViewAdapter(context: Context, users: ArrayList<User>) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>() {
    //vars
    private var mUsers: ArrayList<User> = ArrayList()
    private val mContext: Context
    private var mInterface: IMainActivity? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_main_feed, parent, false)
        return ViewHolder(view)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mInterface = mContext as IMainActivity
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
         val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background) /// ???
        Glide.with(mContext)
            .load(mUsers[position].profile_image)
            .apply(requestOptions)
            .into(holder.image)
//            .placeholder(R.drawable.ic_launcher_background)
        holder.name.text = mUsers[position].name
        holder.interested_in.text = mUsers[position].interested_in
        holder.status.text = mUsers[position].status
        holder.cardView.setOnClickListener {
            Log.d(TAG, "onClick: clicked on: ")
            mInterface?.inflateViewProfileFragment(mUsers[position])
        }

    }



    override fun getItemCount(): Int {
        return mUsers.size
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

    init {
        mContext = context
        mUsers = users
    }

}

