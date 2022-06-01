package com.mertoenjosh.tabiandating.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mertoenjosh.tabiandating.IMainActivity
import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.models.Message


class ChatRecyclerViewAdapter(private val context: Context, messages: ArrayList<Message>) :
    RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder>() {
    //vars
    private var messages: ArrayList<Message> = ArrayList<Message>()
    private var mInterface: IMainActivity? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_chatmessage_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        val message: Message = messages[position]
        val requestOptions: RequestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
        Glide.with(context)
            .load(message.user?.profile_image)
            .apply(requestOptions)
            .into(holder.image)
        holder.message.text = message.message
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mInterface = context as IMainActivity
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var message: TextView

        init {
            image = itemView.findViewById(R.id.profile_image)
            message = itemView.findViewById(R.id.message)
        }
    }

    companion object {
        private const val TAG = "ChatRecyclerViewAd"
    }

    init {
        this.messages = messages
    }
}

















