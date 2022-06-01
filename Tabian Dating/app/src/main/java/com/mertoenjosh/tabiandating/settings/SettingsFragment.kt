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
    private lateinit var fragmentHeading: TextView
    private lateinit var backArrow: RelativeLayout
    private lateinit var name: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var interestedInSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var profileImage: CircleImageView
    private lateinit var save: Button

    //vars
    private var mInterface: IMainActivity? = null
    private var selectedGender: String? = null
    private var selectedInterest: String? = null
    private var selectedStatus: String? = null
    private var selectedImageUrl: String? = null
    private val permissionsChecked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        Log.d(TAG, "onCreateView: started.")
        backArrow = view.findViewById(R.id.back_arrow)
        fragmentHeading = view.findViewById(R.id.fragment_heading)
        name = view.findViewById(R.id.name)
        genderSpinner = view.findViewById(R.id.gender_spinner)
        interestedInSpinner = view.findViewById(R.id.interested_in_spinner)
        statusSpinner = view.findViewById(R.id.relationship_status_spinner)
        profileImage = view.findViewById(R.id.profile_image)
        save = view.findViewById(R.id.btn_save)
        profileImage.setOnClickListener(this)
        save.setOnClickListener(this)
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
                selectedGender = adapterView.getItemAtPosition(pos) as String
            }
            R.id.interested_in_spinner -> {
                Log.d(
                    TAG,
                    "onItemSelected: selected an interest: " + adapterView.getItemAtPosition(pos) as String
                )
                selectedInterest = adapterView.getItemAtPosition(pos) as String
            }
            R.id.relationship_status_spinner -> {
                Log.d(
                    TAG,
                    "onItemSelected: selected a status: " + adapterView.getItemAtPosition(pos) as String
                )
                selectedStatus = adapterView.getItemAtPosition(pos) as String
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
            mInterface?.onBackPressed()
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
        backArrow.setOnClickListener(this)
        fragmentHeading.text = getString(R.string.tag_fragment_settings)
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
        val name = name!!.text.toString()
        if (name != "") {
            editor.putString(Constants.NAME, name)
            editor.apply()
        } else {
            Toast.makeText(requireActivity(), "Enter your name", Toast.LENGTH_SHORT).show()
        }
        editor.putString(Constants.GENDER, selectedGender)
        editor.apply()
        editor.putString(Constants.INTERESTED_IN, selectedInterest)
        editor.apply()
        editor.putString(Constants.RELATIONSHIP_STATUS, selectedStatus)
        editor.apply()
        if (selectedImageUrl != "") {
            editor.putString(Constants.PROFILE_IMAGE, selectedImageUrl)
            editor.apply()
        }
        Toast.makeText(requireActivity(), "saved", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "savePreferences: name: $name")
        Log.d(
            TAG,
            "savePreferences: gender: $selectedGender"
        )
        Log.d(
            TAG,
            "savePreferences: interested in: $selectedInterest"
        )
        Log.d(
            TAG,
            "savePreferences: status: $selectedStatus"
        )
    }

    //
    private val savedPreferences: Unit
        get() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val name = preferences.getString(Constants.NAME, "")
            this.name.setText(name)
            selectedGender =
                preferences.getString(Constants.GENDER, getString(R.string.gender_none))
            val genderArray: Array<String> =
                requireActivity().resources.getStringArray(R.array.gender_array)
            for (i in genderArray.indices) {
                if (genderArray[i] == selectedGender) {
                    genderSpinner.setSelection(i, false)
                }
            }
            selectedInterest = preferences.getString(
                Constants.INTERESTED_IN,
                getString(R.string.interested_in_anyone)
            )
            val interestArray: Array<String> =
                requireActivity().resources.getStringArray(R.array.interested_in_array)
            for (i in interestArray.indices) {
                if (interestArray[i] == selectedInterest) {
                    interestedInSpinner.setSelection(i, false)
                }
            }
            selectedStatus = preferences.getString(
                Constants.RELATIONSHIP_STATUS,
                getString(R.string.status_looking)
            )
            val statusArray: Array<String> =
                requireActivity().resources.getStringArray(R.array.relationship_status_array)
            for (i in statusArray.indices) {
                if (statusArray[i] == selectedStatus) {
                    statusSpinner.setSelection(i, false)
                }
            }
            selectedImageUrl = preferences.getString(Constants.PROFILE_IMAGE, "")
            if (selectedImageUrl != "") {
                Glide.with(this)
                    .load(selectedImageUrl)
                    .into(profileImage)
            }
            //
            genderSpinner.onItemSelectedListener = this
            interestedInSpinner.onItemSelectedListener = this
            statusSpinner.onItemSelectedListener = this
            Log.d(TAG, "getSavedPreferences: name: $name")
            Log.d(
                TAG,
                "getSavedPreferences: gender: $selectedGender"
            )
            Log.d(
                TAG,
                "getSavedPreferences: interested in: $selectedInterest"
            )
            Log.d(
                TAG,
                "getSavedPreferences: status: $selectedStatus"
            )
        }

    companion object {
        private const val TAG = "SettingsFragmentTAG"

        //constants
        private const val NEW_PHOTO_REQUEST = 3567
        private const val VERIFY_PERMISSIONS_REQUEST = 1235
    }
}
















