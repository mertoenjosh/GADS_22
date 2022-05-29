package com.mertoenjosh.tabiandating

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mertoenjosh.tabiandating.models.User


class ViewProfileFragment : Fragment() {
    private var mUser: User?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        mUser = bundle?.getParcelable(getString(R.string.intent_user))
        Log.d(TAG, "onCreate: got incoming user ${mUser?.name}")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_profile, container, false)
        return view
    }

    companion object {
        private const val TAG = "ViewProfileFragmentTAG"
    }
}