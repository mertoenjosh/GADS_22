package com.mertoenjosh.tabiandating

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mertoenjosh.tabiandating.adapters.ChatRecyclerViewAdapter
import com.mertoenjosh.tabiandating.models.Message
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Constants
import de.hdodenhof.circleimageview.CircleImageView


class ChatFragment : Fragment(), View.OnClickListener {
    //widgets
    private lateinit var fragmentHeading: TextView
    private lateinit var profileImage: CircleImageView
    private lateinit var backArrow: RelativeLayout
    private lateinit var newMessage: EditText
    private lateinit var sendMessage: TextView
    private lateinit var relativeLayoutTop: RelativeLayout
    private lateinit var recyclerView: RecyclerView

    //vars
    private var message: Message? = null
    private val messages: ArrayList<Message> = ArrayList()
    private var chatRecyclerViewAdapter: ChatRecyclerViewAdapter? = null
    private var currentUser: User? = null
    private var mInterface: IMainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            message = bundle.getParcelable(getString(R.string.intent_message))
            messages.add(message!!)
            Log.d(TAG, "onCreate: got incoming bundle: " + message!!.user!!.name)
        }
        getSavedPreferences()
    }

    private fun getSavedPreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)

        val name = preferences.getString(Constants.NAME, "")
        currentUser?.name = name!!

        val gender = preferences.getString(Constants.GENDER, getString(R.string.gender_none))
        currentUser?.gender = gender!!

        val profileImageImage = preferences.getString(Constants.PROFILE_IMAGE, "")
        currentUser?.profile_image = profileImageImage!!.toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)
        Log.d(TAG, "onCreateView: started.")
        backArrow = view.findViewById(R.id.back_arrow)
        fragmentHeading = view.findViewById(R.id.fragment_heading)
        profileImage = view.findViewById(R.id.profile_image)
        recyclerView = view.findViewById(R.id.recycler_view)
        sendMessage = view.findViewById(R.id.post_message)
        newMessage = view.findViewById(R.id.input_message)
        relativeLayoutTop = view.findViewById(R.id.relLayoutTop)

        sendMessage.setOnClickListener(this)
        relativeLayoutTop.setOnClickListener(this)

        initToolbar()
        initRecyclerView()
        setBackgroundImage(view)

        return view
    }

    override fun onClick(view: View?) {
        Log.d(TAG, "onClick: clicked.")

        when (view?.id) {
            R.id.back_arrow -> {
                Log.d(TAG, "onClick: navigating back.")
                mInterface?.onBackPressed()
            }

            R.id.post_message -> {
                Log.d(TAG, "onClick: posting new message.")
                messages.add(Message(currentUser, newMessage.text.toString()))
                chatRecyclerViewAdapter!!.notifyDataSetChanged()
                newMessage.setText("")
                recyclerView.smoothScrollToPosition(messages.size - 1)
            }

            R.id.relLayoutTop -> {
                Log.d(TAG, "onClick: navigating back.")
                mInterface!!.inflateViewProfileFragment(message!!.user!!)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mInterface = requireActivity() as IMainActivity
    }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.")
        chatRecyclerViewAdapter = ChatRecyclerViewAdapter(requireContext(), messages)
        recyclerView.adapter = chatRecyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun setBackgroundImage(view: View) {
        val backgroundView: ImageView = view.findViewById(R.id.background)
        Glide.with(requireContext())
            .load(R.drawable.heart_background)
            .into(backgroundView)
    }

    private fun initToolbar() {
        Log.d(TAG, "initToolbar: initializing toolbar.");
        backArrow.setOnClickListener(this)
        fragmentHeading.text = message?.user?.name
        Glide.with(requireContext())
            .load(message?.user?.profile_image)
            .into(profileImage)
    }

    companion object {
        private const val TAG = "ChatFragmentTAG"
    }
}