package com.mertoenjosh.notekeeper

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class NotesListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fab: FloatingActionButton
    private lateinit var rvItemsList: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView

    private val noteLinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val noteRecyclerAdapter by lazy { NoteRecyclerAdapter(this, DataManager.notes) }
    private val courseGridLayoutManager by lazy { GridLayoutManager(this, 2) }
    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        rvItemsList = findViewById(R.id.rvNoteList)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        displayNotes()
        navigationView.setNavigationItemSelectedListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        rvItemsList.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
            R.id.nav_share -> {
                handleSelection("Share")
            }
            R.id.nav_send -> {
                handleSelection("Send")
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displayCourses() {
        rvItemsList.layoutManager = courseGridLayoutManager
        rvItemsList.adapter = courseRecyclerAdapter

        navigationView.menu.findItem(R.id.nav_courses).isChecked = true
    }

    private fun handleSelection(message: String) {
        Log.d(TAG, "handleSelection: $message")
        Snackbar.make(rvItemsList, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayNotes() {
        rvItemsList.layoutManager = noteLinearLayoutManager
        rvItemsList.adapter = noteRecyclerAdapter

        navigationView.menu.findItem(R.id.nav_notes).isChecked = true
    }

    companion object {
        private const val TAG = "NotesListActivityTAG"
    }
}