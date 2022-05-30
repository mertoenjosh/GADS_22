package com.mertoenjosh.tabiandating

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
    private lateinit var mFragmentHeading: TextView
    private lateinit var mProfileImage: CircleImageView
    private lateinit var mBackArrow: RelativeLayout
    private lateinit var mNewMessage: EditText
    private lateinit var mSendMessage: TextView
    private lateinit var mRelativeLayoutTop: RelativeLayout
    private lateinit var mRecyclerView: RecyclerView

    //vars
    private var mMessage: Message? = null
    private val mMessages: ArrayList<Message> = ArrayList()
    private var mChatRecyclerViewAdapter: ChatRecyclerViewAdapter? = null
    private var mCurrentUser: User? = null
    private val mInterface: IMainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            mMessage = bundle.getParcelable(getString(R.string.intent_message))
            mMessages.add(mMessage!!)
            Log.d(TAG, "onCreate: got incoming bundle: " + mMessage!!.user!!.name)
        }
        getSavedPreferences()
    }

    private fun getSavedPreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)

        val name = preferences.getString(Constants.NAME, "")
        mCurrentUser?.name = name!!

        val gender = preferences.getString(Constants.GENDER, getString(R.string.gender_none))
        mCurrentUser?.gender = gender!!

        val profileImageImage = preferences.getString(Constants.PROFILE_IMAGE, "")
        mCurrentUser?.profile_image = profileImageImage!!.toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)
        Log.d(TAG, "onCreateView: started.")
        mBackArrow = view.findViewById(R.id.back_arrow)
        mFragmentHeading = view.findViewById(R.id.fragment_heading)
        mProfileImage = view.findViewById(R.id.profile_image)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mSendMessage = view.findViewById(R.id.post_message)
        mNewMessage = view.findViewById(R.id.input_message)
        mRelativeLayoutTop = view.findViewById(R.id.relLayoutTop)

        mSendMessage.setOnClickListener(this)
        mRelativeLayoutTop.setOnClickListener(this)

        initToolbar()
        initRecyclerView()
        setBackgroundImage(view)

        return view
    }

    private fun setBackgroundImage(view: View) {
        val backgroundView: ImageView = view.findViewById(R.id.background)
        Glide.with(requireContext())
            .load(R.drawable.heart_background)
            .into(backgroundView)
    }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.")
        mChatRecyclerViewAdapter = ChatRecyclerViewAdapter(requireContext(), mMessages)
        mRecyclerView.adapter = mChatRecyclerViewAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initToolbar() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "ChatFragmentTAG"
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}