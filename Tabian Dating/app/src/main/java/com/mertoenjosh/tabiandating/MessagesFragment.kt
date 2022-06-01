package com.mertoenjosh.tabiandating;

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mertoenjosh.tabiandating.adapters.MessagesRecyclerViewAdapter
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Constants
import com.mertoenjosh.tabiandating.util.Users


class MessagesFragment: Fragment() {
    //widgets
    private var recyclerViewAdapter: MessagesRecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    //vars
    private val users: ArrayList<User>  = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        Log.d(TAG, "onCreateView: started.")
        recyclerView = view.findViewById(R.id.recycler_view)
        searchView = view.findViewById(R.id.action_search)

        getConnections()
        initSearchView()
        return view
    }

    private fun initSearchView(){
        // Associate searchable configuration with the SearchView
        // val searchManager: SearchManager = getActivity().getSystemService(Context.SEARCH_SERVICE);
        val searchManager: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.maxWidth = Integer.MAX_VALUE;


        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                recyclerViewAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // filter recycler view when text is changed
                recyclerViewAdapter?.filter?.filter(newText)
                return false
            }

        })
    }

    private fun getConnections(){
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val savedNames: Set<String>  =
            preferences.getStringSet(Constants.SAVED_CONNECTIONS, HashSet<String>()) as Set<String>
        val users = Users()
        if(this.users != null){
            this.users.clear()
        }
        for(user in users.USERS){
            if(savedNames.contains(user.name)){
                this.users.add(user)
            }
        }
        if(recyclerViewAdapter == null){
            initRecyclerView()
        }
    }

    private fun initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
//        mRecyclerViewAdapter = MessagesRecyclerViewAdapter(requireContext(), mUsers)
        recyclerViewAdapter = MessagesRecyclerViewAdapter(requireContext(), users, users)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        private const val TAG = "MessagesFragmentTAG"
    }


}
