package com.mertoenjosh.tabiandating

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mertoenjosh.tabiandating.adapters.MainRecyclerViewAdapter
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Constants
import com.mertoenjosh.tabiandating.util.Users

class SavedConnectionsFragment : Fragment() {
    //widgets
    private var recyclerViewAdapter: MainRecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null

    //vars
    private val users: ArrayList<User> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_connections, container, false)
        Log.d(TAG, "onCreateView: started.")
        recyclerView = view.findViewById(R.id.recycler_view)
        connections
        return view
    }

    private val connections: Unit
        get() {
            val preferences = PreferenceManager.getDefaultSharedPreferences(activity)
            val savedNames = preferences.getStringSet(Constants.SAVED_CONNECTIONS, HashSet())
            val users = Users()
            this.users?.clear()

            for (user in users.USERS) {
                if (savedNames!!.contains(user.name))
                    this.users.add(user)
            }

            if (recyclerViewAdapter == null) {
                initRecyclerView()
            }
        }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.")
        recyclerViewAdapter = MainRecyclerViewAdapter(requireContext(), users)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(NUM_GRID_COLUMNS, LinearLayoutManager.VERTICAL)
        recyclerView?.adapter = recyclerViewAdapter
        recyclerView?.layoutManager = staggeredGridLayoutManager
    }

    companion object {
        private const val TAG = "SavedConnFragment"

        //constants
        private const val NUM_GRID_COLUMNS = 2
    }
}