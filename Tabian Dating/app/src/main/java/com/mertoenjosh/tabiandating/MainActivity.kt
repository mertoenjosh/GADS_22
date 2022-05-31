package com.mertoenjosh.tabiandating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mertoenjosh.tabiandating.models.Message
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.settings.SettingsFragment
import com.mertoenjosh.tabiandating.util.Constants

class MainActivity : AppCompatActivity(),
    IMainActivity,
    BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener
{
     private lateinit var bottomNavigationViewEx: BottomNavigationView
     private lateinit var navigationView: NavigationView
     private lateinit var headerImage: ImageView
     private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationViewEx = findViewById(R.id.bottom_nav_view)
        navigationView = findViewById(R.id.navigation_view)
        val headerView = navigationView.getHeaderView(0)
        headerImage = headerView.findViewById(R.id.header_image)
        drawerLayout = findViewById(R.id.drawer_layout)
        Log.d(TAG, "onCreate: started")
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(this)
        isFirstLogin()
        initBottomNavView()
        initialize()
        setHeaderImage()
        setNavigationViewListener()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                initialize()
            }
            R.id.settings -> {
                Log.d(TAG, "onNavigationItemSelected: Settings Fragment")
                val settingsFragment = SettingsFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_content_frame, settingsFragment, getString(R.string.tag_fragment_settings))
                    addToBackStack(getString(R.string.tag_fragment_home))
                    commit()
                }
            }
            R.id.agreement -> {
                Log.d(TAG, "onNavigationItemSelected: Home Fragment")
                val agreementFragment = AgreementFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_content_frame, agreementFragment, getString(R.string.tag_fragment_agreement))
                    addToBackStack(getString(R.string.tag_fragment_home))
                    commit()
                }
            }

            R.id.bottom_nav_home -> {
                Log.d(TAG, "onNavigationItemSelected: Home Fragment")
                val homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home))
                    addToBackStack(getString(R.string.tag_fragment_home))
                    commit()
                }
                item.isChecked = true
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
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
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
        val fragment = ChatFragment()
        val args = Bundle()
        args.putParcelable(getString(R.string.intent_message), message)
        fragment.arguments = args
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_content_frame, fragment, getString(R.string.tag_fragment_chat))
            addToBackStack(getString(R.string.tag_fragment_chat))
            commit()
        }
    }

    private fun initialize () {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_content_frame, homeFragment, getString(R.string.tag_fragment_home))
            addToBackStack(getString(R.string.tag_fragment_home))
            commit()
        }
    }

    private fun setHeaderImage() {
        Log.d(TAG, "setHeaderImage: setting header image for navigation view")
        Glide.with(this)
            .load(R.drawable.couple)
            .into(headerImage)
    }

    private fun initBottomNavView() {
        Log.d(TAG, "initBottomNavView: initializing bottom navigation view")
    }
    private fun setNavigationViewListener() {
        Log.d(TAG, "setNavigationViewListener: initializing the navigation drawer listener")
        navigationView.setNavigationItemSelectedListener(this)
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
