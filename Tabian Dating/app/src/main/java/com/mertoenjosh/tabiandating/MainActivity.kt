package com.mertoenjosh.tabiandating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mertoenjosh.tabiandating.models.FragmentTag
import com.mertoenjosh.tabiandating.models.Message
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.settings.SettingsFragment
import com.mertoenjosh.tabiandating.util.Constants

class MainActivity : AppCompatActivity(),
    IMainActivity,
    BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener
{
    // Fragments
    private var homeFragment: HomeFragment? = null
    private var savedConnectionsFragment: SavedConnectionsFragment? = null
    private var messagesFragment: MessagesFragment? = null
    private var settingsFragment: SettingsFragment? = null
    private var viewProfileFragment: ViewProfileFragment? = null
    private var chatFragment: ChatFragment? = null
    private var agreementFragment: AgreementFragment? = null

    // widgets
    private lateinit var bottomNavigationViewEx: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var headerImage: ImageView
    private lateinit var drawerLayout: DrawerLayout

    // variables
    private var fragmentTags: ArrayList<String> = ArrayList()
    private var fragments: ArrayList<FragmentTag> = ArrayList()
    private var exitCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: started")
        setContentView(R.layout.activity_main)

        bottomNavigationViewEx = findViewById(R.id.bottom_nav_view)
        navigationView = findViewById(R.id.navigation_view)
        val headerView = navigationView.getHeaderView(0)
        headerImage = headerView.findViewById(R.id.header_image)



        drawerLayout = findViewById(R.id.drawer_layout)

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(this)
        isFirstLogin()
        initBottomNavView()
        initialize()
        setHeaderImage()
        setNavigationViewListener()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val backStackCount = fragmentTags.size
        if (backStackCount > 1) {
            val topFragmentTag = fragmentTags[backStackCount - 1]
            val newTopFragmentTag = fragmentTags[backStackCount - 2]
            setFragmentVisibilities(newTopFragmentTag)
            fragmentTags.remove(topFragmentTag)
            exitCount = 0
        } else if (backStackCount == 1){
            val topFragmentTag = fragmentTags[backStackCount - 1]
            if (topFragmentTag == getString(R.string.tag_fragment_home)) {
                homeFragment!!.scrollToTop()
                exitCount++
                Toast.makeText(this, "click again to exit", Toast.LENGTH_SHORT).show()
            } else {
                exitCount++
                Toast.makeText(this, "click again to exit", Toast.LENGTH_SHORT).show()
            }
            exitCount++
            Toast.makeText(this, "click again to exit", Toast.LENGTH_SHORT).show()
        }

        if (exitCount >= 2) {
            super.onBackPressed()
        }
    }

    private fun setNavigationIcon(tagname: String) {
        val menu = bottomNavigationViewEx.menu
        val menuItem: MenuItem?

        when (tagname) {
            getString(R.string.tag_fragment_home) -> {
                Log.d(TAG, "setNavigationIcon: Home fragment is visible")
                menuItem = menu.getItem(HOME_FRAGMENT)
                menuItem.isChecked = true
            }
            getString(R.string.tag_fragment_saved_connections) -> {
                Log.d(TAG, "setNavigationIcon: Connections fragment is visible")
                menuItem = menu.getItem(CONNECTIONS_FRAGMENT)
                menuItem.isChecked = true
            }
            getString(R.string.tag_fragment_messages) -> {
                Log.d(TAG, "setNavigationIcon: Messages fragment is visible")
                menuItem = menu.getItem(MESSAGES_FRAGMENT)
                menuItem.isChecked = true
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                fragmentTags.clear()
                fragmentTags = ArrayList()
                initialize()
            }
            R.id.settings -> {
                Log.d(TAG, "onNavigationItemSelected: Settings Fragment")
                if (settingsFragment == null) {
                    settingsFragment = SettingsFragment()
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.main_content_frame, settingsFragment!!, getString(R.string.tag_fragment_settings))
                        commit()
                        fragmentTags.add(getString(R.string.settings))
                        fragments.add(FragmentTag(settingsFragment, getString(R.string.settings)))
                    }
                } else {
                    fragmentTags.remove(getString(R.string.settings))
                    fragmentTags.add(getString(R.string.settings))
                }
                setFragmentVisibilities(getString(R.string.settings))
            }
            R.id.agreement -> {
                Log.d(TAG, "onNavigationItemSelected: Home Fragment")
                if (agreementFragment == null) {
                    agreementFragment = AgreementFragment()
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.main_content_frame, agreementFragment!!, getString(R.string.tag_fragment_agreement))
                        commit()
                        fragmentTags.add(getString(R.string.tag_fragment_agreement))
                        fragments.add(FragmentTag(agreementFragment, getString(R.string.tag_fragment_agreement)))
                    }
                } else {
                    fragmentTags.remove(getString(R.string.tag_fragment_agreement))
                    fragmentTags.add(getString(R.string.tag_fragment_agreement))
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_agreement))
            }

            R.id.bottom_nav_home -> {
                Log.d(TAG, "onNavigationItemSelected: Home Fragment")
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.main_content_frame, homeFragment!!, getString(R.string.tag_fragment_home))
                        commit()
                        fragmentTags.add(getString(R.string.tag_fragment_home))
                        fragments.add(FragmentTag(homeFragment, getString(R.string.tag_fragment_home)))
                    }
                } else {
                    fragmentTags.remove(getString(R.string.tag_fragment_home))
                    fragmentTags.add(getString(R.string.tag_fragment_home))
                }
                item.isChecked = true
                setFragmentVisibilities(getString(R.string.tag_fragment_home))
            }
            R.id.bottom_nav_connections -> {
                Log.d(TAG, "onNavigationItemSelected: Connections Fragment")
                if (savedConnectionsFragment == null) {
                    savedConnectionsFragment = SavedConnectionsFragment()
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.main_content_frame, savedConnectionsFragment!!, getString(R.string.tag_fragment_saved_connections))
                        commit()
                        fragmentTags.add(getString(R.string.tag_fragment_saved_connections))
                        fragments.add(FragmentTag(savedConnectionsFragment, getString(R.string.tag_fragment_saved_connections)))
                    }
                } else {
                    fragmentTags.remove(getString(R.string.tag_fragment_saved_connections))
                    fragmentTags.add(getString(R.string.tag_fragment_saved_connections))
                }
                item.isChecked = true
                setFragmentVisibilities(getString(R.string.tag_fragment_saved_connections))
            }
            R.id.bottom_nav_messages -> {
                Log.d(TAG, "onNavigationItemSelected: Messages Fragment")
                if (messagesFragment == null) {
                    messagesFragment = MessagesFragment()
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.main_content_frame, messagesFragment!!, getString(R.string.tag_fragment_messages))
                        commit()
                        fragmentTags.add(getString(R.string.tag_fragment_messages))
                        fragments.add(FragmentTag(messagesFragment, getString(R.string.tag_fragment_messages)))
                    }
                } else {
                    fragmentTags.remove(getString(R.string.tag_fragment_messages))
                    fragmentTags.add(getString(R.string.tag_fragment_messages))
                }
                item.isChecked = true
                setFragmentVisibilities(getString(R.string.tag_fragment_messages))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return false
    }


    override fun inflateViewProfileFragment(user: User) {
        if (viewProfileFragment != null) {
            supportFragmentManager.beginTransaction().remove(viewProfileFragment!!).commitAllowingStateLoss()
        }
        viewProfileFragment = ViewProfileFragment()
        val args = Bundle()
        args.putParcelable(getString(R.string.intent_user), user)
        viewProfileFragment!!.arguments = args
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_content_frame, viewProfileFragment!!, getString(R.string.tag_fragment_view_profile))
            commit()
            fragmentTags.add(getString(R.string.tag_fragment_view_profile))
            fragments.add(FragmentTag(viewProfileFragment, getString(R.string.tag_fragment_view_profile)))
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_view_profile))
    }

    override fun onMessageSelected(message: Message) {
        if (chatFragment != null) {
            supportFragmentManager.beginTransaction().remove(chatFragment!!).commitAllowingStateLoss()
        }
        chatFragment = ChatFragment()
        val args = Bundle()
        args.putParcelable(getString(R.string.intent_message), message)
        chatFragment!!.arguments = args
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_content_frame, chatFragment!!, getString(R.string.tag_fragment_chat))
            commit()
            fragmentTags.add(getString(R.string.tag_fragment_chat))
            fragments.add(FragmentTag(chatFragment, getString(R.string.tag_fragment_chat)))
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_chat))
    }

    private fun initialize () {
        if (homeFragment == null) {
            homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.main_content_frame, homeFragment!!, getString(R.string.tag_fragment_home))
                commit()
                fragmentTags.add(getString(R.string.tag_fragment_home))
                fragments.add(FragmentTag(homeFragment, getString(R.string.tag_fragment_home)))
            }
        } else {
            fragmentTags.remove(getString(R.string.tag_fragment_home))
            fragmentTags.add(getString(R.string.tag_fragment_home))
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_home))
    }

    private fun setFragmentVisibilities(tagname: String) {
        when (tagname) {
            getString(R.string.tag_fragment_home) -> showBottomNavigation()
            getString(R.string.tag_fragment_saved_connections) -> showBottomNavigation()
            getString(R.string.tag_fragment_messages) -> showBottomNavigation()
            getString(R.string.tag_fragment_settings) -> hideBottomNavigation()
            getString(R.string.tag_fragment_view_profile) -> hideBottomNavigation()
            getString(R.string.tag_fragment_chat) -> hideBottomNavigation()
            getString(R.string.tag_fragment_agreement) -> hideBottomNavigation()
        }

        for (i in fragments) {
            if (tagname == i.tag) {
                // show
                supportFragmentManager.beginTransaction().apply {
                    i.fragment?.let { show(it) }
                    commit()
                }
            } else {
                // don't show
                supportFragmentManager.beginTransaction().apply {
                    i.fragment?.let { hide(it) }
                    commit()
                }
            }
        }
        setNavigationIcon(tagname)
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

    private fun hideBottomNavigation() {
        bottomNavigationViewEx.visibility = View.GONE
    }
    private fun showBottomNavigation() {
        bottomNavigationViewEx.visibility = View.VISIBLE
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
        private const val HOME_FRAGMENT = 0
        private const val CONNECTIONS_FRAGMENT = 1
        private const val MESSAGES_FRAGMENT = 2
    }


}
