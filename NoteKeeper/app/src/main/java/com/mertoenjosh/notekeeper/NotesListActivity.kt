package com.mertoenjosh.notekeeper

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class NotesListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NoteRecyclerAdapter.OnNoteSelectedListener
{
    private lateinit var fab: FloatingActionButton
    private lateinit var rvItemsList: RecyclerView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView

    private val noteLinearLayoutManager by lazy { LinearLayoutManager(this) }
    private val noteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(this, DataManager.loadNotes())
        adapter.setOnSelectedListener(this)
        adapter
    }
    private val courseGridLayoutManager by lazy { GridLayoutManager(this, 2) }
    private val viewModel by lazy { ViewModelProvider(this).get(NotesListActivityViewModel::class.java) }
    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }
    private val recentlyViewedNoteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(this, viewModel.recentlyViewed)
        adapter.setOnSelectedListener(this)
        adapter
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
        navigationView.setNavigationItemSelectedListener(this)

        if (viewModel.isNewlyCreated && savedInstanceState != null)
            viewModel.restoreState(savedInstanceState)
        viewModel.isNewlyCreated = false
        handleDisplaySelection(viewModel.navDrawerDisplaySelection)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)

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

    override fun onNoteSelected(note: NoteInfo) {
        viewModel.addToRecentlyViewedNotes(note)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notes,
            R.id.nav_courses,
            R.id.nav_recent -> {
                handleDisplaySelection(item.itemId)
                viewModel.navDrawerDisplaySelection = item.itemId
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

    private fun handleDisplaySelection(itemId: Int) {
        when(itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
            R.id.nav_recent -> {
                displayRecentlyViewedNotes()
            }
        }
    }

    private fun displayRecentlyViewedNotes() {
        rvItemsList.layoutManager = noteLinearLayoutManager
        rvItemsList.adapter = recentlyViewedNoteRecyclerAdapter

        navigationView.menu.findItem(R.id.nav_recent).isChecked = true
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