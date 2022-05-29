package com.mertoenjosh.tabiandating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mertoenjosh.tabiandating.models.Message
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Constants

class MainActivity : AppCompatActivity(), IMainActivity, BottomNavigationView.OnNavigationItemSelectedListener {
     private lateinit var bottomNavigationViewEx: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationViewEx = findViewById(R.id.bottom_nav_view)
        Log.d(TAG, "onCreate: started")

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(this)
        isFirstLogin()
        initBottomNavView()
        initialize()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bottom_nav_home -> {
                Log.d(TAG, "onNavigationItemSelected: Home Fragment")
                val homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home))
                    addToBackStack(getString(R.string.tag_fragment_home))
                    commit()
                }

                item.isChecked = true
                true
            }
            R.id.bottom_nav_connections -> {
                Log.d(TAG, "onNavigationItemSelected: Connections Fragment")
                val savedConnectionsFragment = SavedConnectionsFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_content_frame, savedConnectionsFragment, getString(R.string.tag_fragment_saved_connections))
                    addToBackStack(getString(R.string.tag_fragment_home))
                    commit()
                }

                item.isChecked = true
                true
            }
            R.id.bottom_nav_messages -> {
                Log.d(TAG, "onNavigationItemSelected: Messages Fragment")
                val messagesFragment = MessagesFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_content_frame, messagesFragment, getString(R.string.tag_fragment_messages))
                    addToBackStack(getString(R.string.tag_fragment_home))
                    commit()
                }
                item.isChecked = true
                true
            }
            else -> false
        }
    }


    override fun inflateViewProfileFragment(user: User) {
        val fragment = ViewProfileFragment()
        val args = Bundle()
        args.putParcelable(getString(R.string.intent_user), user)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_content_frame, fragment, getString(R.string.tag_fragment_home))
            addToBackStack(getString(R.string.tag_fragment_view_profile))
            commit()
        }
    }

    override fun onMessageSelected(message: Message) {
        TODO("Not yet implemented")
    }

    private fun initialize () {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home))
            addToBackStack(getString(R.string.tag_fragment_home))
            commit()
        }

    }

    private fun initBottomNavView() {
        Log.d(TAG, "initBottomNavView: initializing bottom navigation view")
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
