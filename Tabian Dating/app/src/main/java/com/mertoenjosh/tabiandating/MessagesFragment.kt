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
    private var mRecyclerViewAdapter: MessagesRecyclerViewAdapter? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSearchView: SearchView

    //vars
    private val mUsers: ArrayList<User>  = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        Log.d(TAG, "onCreateView: started.")
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mSearchView = view.findViewById(R.id.action_search)

        getConnections()
        initSearchView()
        return view
    }

    private fun initSearchView(){
        // Associate searchable configuration with the SearchView
        // val searchManager: SearchManager = getActivity().getSystemService(Context.SEARCH_SERVICE);
        val searchManager: SearchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        mSearchView.maxWidth = Integer.MAX_VALUE;


        // listening to search query text change
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                mRecyclerViewAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // filter recycler view when text is changed
                mRecyclerViewAdapter?.filter?.filter(newText)
                return false
            }

        })
    }

    private fun getConnections(){
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val savedNames: Set<String>  =
            preferences.getStringSet(Constants.SAVED_CONNECTIONS, HashSet<String>()) as Set<String>
        val users = Users()
        if(mUsers != null){
            mUsers.clear()
        }
        for(user in users.USERS){
            if(savedNames.contains(user.name)){
                mUsers.add(user)
            }
        }
        if(mRecyclerViewAdapter == null){
            initRecyclerView()
        }
    }

    private fun initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
//        mRecyclerViewAdapter = MessagesRecyclerViewAdapter(requireContext(), mUsers)
        mRecyclerViewAdapter = MessagesRecyclerViewAdapter(requireContext(), mUsers, mUsers)
        mRecyclerView.adapter = mRecyclerViewAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    companion object {
        private const val TAG = "MessagesFragmentTAG"
    }


}
