package com.mertoenjosh.tabiandating

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide


class AgreementFragment : Fragment(), View.OnClickListener {
    //constants
    //widgets
    private var mFragmentHeading: TextView? = null
    private var mBackArrow: RelativeLayout? = null

    //vars
    private var mInterface: IMainActivity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_agreement, container, false)
        Log.d(TAG, "onCreateView: started.")
        mBackArrow = view.findViewById(R.id.back_arrow)
        mFragmentHeading = view.findViewById(R.id.fragment_heading)
        initToolbar()
        setBackgroundImage(view)
        return view
    }

    private fun setBackgroundImage(view: View) {
        val backgroundView = view.findViewById<ImageView>(R.id.background)
        Glide.with(requireContext())
            .load(R.drawable.heart_background)
            .into(backgroundView)
    }

    private fun initToolbar() {
        Log.d(TAG, "initToolbar: initializing toolbar.")
        mBackArrow!!.setOnClickListener(this)
        mFragmentHeading?.text = getString(R.string.tag_fragment_agreement)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "onClick: clicked.")
        if (view.id == R.id.back_arrow) {
            Log.d(TAG, "onClick: navigating back.")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mInterface = activity as IMainActivity?
    }

    companion object {
        private const val TAG = "AgreementFragment"
    }
}