package com.mertoenjosh.tabiandating

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Constants
import de.hdodenhof.circleimageview.CircleImageView


class ViewProfileFragment : Fragment(), OnLikeListener, View.OnClickListener {
    private lateinit var backArrow: RelativeLayout
    private lateinit var fragmentHeading: TextView
    private lateinit var profileImage: CircleImageView
    private lateinit var likeButton: ImageView
    private lateinit var name: TextView
    private lateinit var gender: TextView
    private lateinit var interestedIn: TextView
    private lateinit var status: TextView

    private var isLiked: Boolean= false
    private var user: User?  = null
    private var mInterface: IMainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        user = bundle?.getParcelable(getString(R.string.intent_user))
        Log.d(TAG, "onCreate: got incoming user ${user?.name}")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_profile, container, false)
        backArrow = view.findViewById(R.id.back_arrow)
        fragmentHeading = view.findViewById(R.id.fragment_heading)
        profileImage = view.findViewById(R.id.profile_image)
        likeButton = view.findViewById(R.id.heart_button)
        name = view.findViewById(R.id.name)
        gender = view.findViewById(R.id.gender)
        interestedIn = view.findViewById(R.id.interested_in)
        status = view.findViewById(R.id.status)

        isLiked = ifConnected()
        setLikeImage()
        likeButton.setOnClickListener(this)
        backArrow.setOnClickListener(this)
        setBackgroundImage(view)
        initialize()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mInterface = context as IMainActivity
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "onClick: clicked")
        when (v) {
            backArrow -> {
                Log.d(TAG, "onClick: navigating back")
                mInterface?.onBackPressed()
            }

            likeButton -> {
                val imageView = likeButton
                isLiked = if (isLiked) {
                    unLiked(imageView)
                    false
                } else {
                    liked(imageView)
                    true
                }
                setLikeImage()
            }
        }

    }

    override fun liked(likeButton: ImageView) {
        Log.d(TAG, "liked: Liked")

        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = preferences.edit()
        val savedNames = preferences.getStringSet(Constants.SAVED_CONNECTIONS, HashSet<String>())
        savedNames?.add(user?.name)
        editor.putStringSet(Constants.SAVED_CONNECTIONS, savedNames)
        editor.apply()

    }

    override fun unLiked(likeButton: ImageView) {
        Log.d(TAG, "unLiked: unLiked")
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = preferences.edit()
        val savedNames = preferences.getStringSet(Constants.SAVED_CONNECTIONS, HashSet<String>())
        savedNames?.remove(user?.name)
        editor.apply()

        editor.putStringSet(Constants.SAVED_CONNECTIONS, savedNames)
        editor.apply()
    }

    private fun setLikeImage() {
        if (isLiked) {
            likeButton.setImageResource(R.drawable.ic_favorite)
        } else {
            likeButton.setImageResource(R.drawable.ic_heart_outline)
        }
    }

    private fun ifConnected(): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val savedNames = preferences.getStringSet(Constants.SAVED_CONNECTIONS, HashSet<String>())

        return savedNames!!.contains(user?.name)
    }

    private fun initialize() {
        if (user != null) {
            Glide.with(requireContext())
                .load(user!!.profile_image)
                .into(profileImage)

            name.text = user!!.name
            gender.text = user!!.gender
            interestedIn.text = user!!.interested_in
            status.text = user!!.status
        }
    }

    private fun setBackgroundImage(view: View) {
        val backgroundImage: ImageView = view.findViewById(R.id.background)
        Glide.with(requireContext())
            .load(R.drawable.heart_background)
            .into(backgroundImage)
    }

    companion object {
        private const val TAG = "ViewProfileFragmentTAG"
    }
}