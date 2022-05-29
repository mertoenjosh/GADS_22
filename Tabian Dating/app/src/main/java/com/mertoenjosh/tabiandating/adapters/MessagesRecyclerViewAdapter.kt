package com.mertoenjosh.tabiandating.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mertoenjosh.tabiandating.IMainActivity
import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.models.Message
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Messages
import java.util.*


class MessagesRecyclerViewAdapter(
    private val context: Context,
    private val users: ArrayList<User>,
    private var filteredUsers: ArrayList<User>,
) : RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder>(), Filterable {
    private val mInterface: IMainActivity? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_messages_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")

        val user: User = filteredUsers[position]

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)

        Glide.with(context)
            .load(user.profile_image)
            .apply(requestOptions)
            .into(holder.image)

        holder.name.text = user.name
        holder.message.text = Messages.MESSAGES[position] //generate a random message


        holder.parent?.setOnClickListener {
            Log.d(TAG, "onClick: clicked on: ${user.name}")
            mInterface?.onMessageSelected(Message(user, Messages.MESSAGES[position]))
        }
    }

    override fun getItemCount(): Int  = filteredUsers.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredUsers = users
                } else {
                    val filteredList: ArrayList<User> = ArrayList()
                    for (row in users) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.lowercase(Locale.getDefault())
                                .contains(charString.lowercase(Locale.getDefault()))
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredUsers = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredUsers
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredUsers = filterResults.values as ArrayList<User>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var image: ImageView =  view.findViewById(R.id.image)
        var name: TextView = view.findViewById(R.id.name)
        var message: TextView = view.findViewById(R.id.message)
        var reply: TextView = view.findViewById(R.id.reply)
        var parent: RelativeLayout? = view.findViewById(R.id.parent_view)

    }

    companion object {
        private const val TAG = "ConnectionsAdapterTAG"
    }
}