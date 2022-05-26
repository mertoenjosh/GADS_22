package com.mertoenjosh.tabiandating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.mertoenjosh.tabiandating.util.Constants

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: started")
        isFirstLogin()
        initialize()

    }

    private fun initialize () {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home))
            addToBackStack(getString(R.string.tag_fragment_home))
            commit()
        }

    }

    private fun isFirstLogin() {
        Log.d(TAG, "isFirstLogin: Check if its the first time logging in")
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isFirstLogin = preferences.getBoolean(Constants.FIRST_TIME_LOGIN, true)

        if (isFirstLogin) {
            Log.d(TAG, "isFirstLogin: launching alert dialog.")
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage(getString(R.string.first_time_user_message))
            alertDialog.setPositiveButton("OK") { dialogInterface, _ ->
                Log.d(TAG, "isFirstLogin: closing dialog")
                val editor = preferences.edit()
                editor.putBoolean(Constants.FIRST_TIME_LOGIN, false);
                editor.apply()
                dialogInterface.dismiss()
            }

            alertDialog.setIcon(R.drawable.ic_celebrations_24)
            alertDialog.setTitle(" ")
            alertDialog.show()
        }
    }
    
    companion object {
        private const val TAG = "MainActivityTAG"
    }

}