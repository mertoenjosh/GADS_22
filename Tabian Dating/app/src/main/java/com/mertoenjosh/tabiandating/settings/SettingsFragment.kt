package com.mertoenjosh.tabiandating.settings


import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mertoenjosh.tabiandating.IMainActivity
import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.util.Constants
import de.hdodenhof.circleimageview.CircleImageView


class SettingsFragment : Fragment(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    //widgets
    private lateinit var mFragmentHeading: TextView
    private lateinit var mBackArrow: RelativeLayout
    private lateinit var mName: EditText
    private lateinit var mGenderSpinner: Spinner
    private lateinit var mInterestedInSpinner: Spinner
    private lateinit var mStatusSpinner: Spinner
    private lateinit var mProfileImage: CircleImageView
    private lateinit var mSave: Button

    //vars
    private var mInterface: IMainActivity? = null
    private var mSelectedGender: String? = null
    private var mSelectedInterest: String? = null
    private var mSelectedStatus: String? = null
    private var mSelectedImageUrl: String? = null
    private val mPermissionsChecked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        Log.d(TAG, "onCreateView: started.")
        mBackArrow = view.findViewById(R.id.back_arrow)
        mFragmentHeading = view.findViewById(R.id.fragment_heading)
        mName = view.findViewById(R.id.name)
        mGenderSpinner = view.findViewById(R.id.gender_spinner)
        mInterestedInSpinner = view.findViewById(R.id.interested_in_spinner)
        mStatusSpinner = view.findViewById(R.id.relationship_status_spinner)
        mProfileImage = view.findViewById(R.id.profile_image)
        mSave = view.findViewById(R.id.btn_save)
        mProfileImage.setOnClickListener(this)
        mSave.setOnClickListener(this)
        setBackgroundImage(view)
        initToolbar()
        savedPreferences
        return view
    }
    override fun onItemSelected(adapterView: AdapterView<*>, view: View, pos: Int, id: Long) {
        Log.d(TAG, "onItemSelected: clicked.")
        when (adapterView.id) {
            R.id.gender_spinner -> {
                Log.d(
                    TAG,
                    "onItemSelected: selected a gender: " + adapterView.getItemAtPosition(pos) as String
                )
                mSelectedGender = adapterView.getItemAtPosition(pos) as String
            }
            R.id.interested_in_spinner -> {
                Log.d(
                    TAG,
                    "onItemSelected: selected an interest: " + adapterView.getItemAtPosition(pos) as String
                )
                mSelectedInterest = adapterView.getItemAtPosition(pos) as String
            }
            R.id.relationship_status_spinner -> {
                Log.d(
                    TAG,
                    "onItemSelected: selected a status: " + adapterView.getItemAtPosition(pos) as String
                )
                mSelectedStatus = adapterView.getItemAtPosition(pos) as String
            }
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        Log.d(TAG, "onNothingSelected: nothing is selected.")
    }



    override fun onClick(view: View) {
        Log.d(TAG, "onClick: clicked.")
        if (view.id == R.id.back_arrow) {
            Log.d(TAG, "onClick: navigating back.")
        }
        if (view.id == R.id.btn_save) {
            Log.d(TAG, "onClick: saving.")
            savePreferences()
        }
        if (view.id == R.id.profile_image) {
            Log.d(TAG, "onClick: opening activity to choose a photo.")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mInterface = activity as IMainActivity?
    }

    private fun initToolbar() {
        Log.d(TAG, "initToolbar: initializing toolbar.")
        mBackArrow.setOnClickListener(this)
        mFragmentHeading.text = getString(R.string.tag_fragment_settings)
    }

    private fun setBackgroundImage(view: View) {
        val backgroundView = view.findViewById<ImageView>(R.id.background)
        Glide.with(requireContext())
            .load(R.drawable.heart_background)
            .into(backgroundView)
    }

    private fun savePreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor = preferences.edit()
        val name = mName!!.text.toString()
        if (name != "") {
            editor.putString(Constants.NAME, name)
            editor.apply()
        } else {
            Toast.makeText(requireActivity(), "Enter your name", Toast.LENGTH_SHORT).show()
        }
        editor.putString(Constants.GENDER, mSelectedGender)
        editor.apply()
        editor.putString(Constants.INTERESTED_IN, mSelectedInterest)
        editor.apply()
        editor.putString(Constants.RELATIONSHIP_STATUS, mSelectedStatus)
        editor.apply()
        if (mSelectedImageUrl != "") {
            editor.putString(Constants.PROFILE_IMAGE, mSelectedImageUrl)
            editor.apply()
        }
        Toast.makeText(requireActivity(), "saved", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "savePreferences: name: $name")
        Log.d(
            TAG,
            "savePreferences: gender: $mSelectedGender"
        )
        Log.d(
            TAG,
            "savePreferences: interested in: $mSelectedInterest"
        )
        Log.d(
            TAG,
            "savePreferences: status: $mSelectedStatus"
        )
    }

    //
    private val savedPreferences: Unit
        get() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val name = preferences.getString(Constants.NAME, "")
            mName.setText(name)
            mSelectedGender =
                preferences.getString(Constants.GENDER, getString(R.string.gender_none))
            val genderArray: Array<String> =
                requireActivity().resources.getStringArray(R.array.gender_array)
            for (i in genderArray.indices) {
                if (genderArray[i] == mSelectedGender) {
                    mGenderSpinner.setSelection(i, false)
                }
            }
            mSelectedInterest = preferences.getString(
                Constants.INTERESTED_IN,
                getString(R.string.interested_in_anyone)
            )
            val interestArray: Array<String> =
                requireActivity().resources.getStringArray(R.array.interested_in_array)
            for (i in interestArray.indices) {
                if (interestArray[i] == mSelectedInterest) {
                    mInterestedInSpinner.setSelection(i, false)
                }
            }
            mSelectedStatus = preferences.getString(
                Constants.RELATIONSHIP_STATUS,
                getString(R.string.status_looking)
            )
            val statusArray: Array<String> =
                requireActivity().resources.getStringArray(R.array.relationship_status_array)
            for (i in statusArray.indices) {
                if (statusArray[i] == mSelectedStatus) {
                    mStatusSpinner.setSelection(i, false)
                }
            }
            mSelectedImageUrl = preferences.getString(Constants.PROFILE_IMAGE, "")
            if (mSelectedImageUrl != "") {
                Glide.with(this)
                    .load(mSelectedImageUrl)
                    .into(mProfileImage)
            }
            //
            mGenderSpinner.onItemSelectedListener = this
            mInterestedInSpinner.onItemSelectedListener = this
            mStatusSpinner.onItemSelectedListener = this
            Log.d(TAG, "getSavedPreferences: name: $name")
            Log.d(
                TAG,
                "getSavedPreferences: gender: $mSelectedGender"
            )
            Log.d(
                TAG,
                "getSavedPreferences: interested in: $mSelectedInterest"
            )
            Log.d(
                TAG,
                "getSavedPreferences: status: $mSelectedStatus"
            )
        }

    companion object {
        private const val TAG = "SettingsFragmentTAG"

        //constants
        private const val NEW_PHOTO_REQUEST = 3567
        private const val VERIFY_PERMISSIONS_REQUEST = 1235
    }
}
















