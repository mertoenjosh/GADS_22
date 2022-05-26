package com.mertoenjosh.tabiandating

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mertoenjosh.tabiandating.adapters.MainRecyclerViewAdapter
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Users

class HomeFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private val mMatches: ArrayList<User> = ArrayList()
    private var mainRvAdapter: MainRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        Log.d(TAG, "onCreateView: Started.")
        mRecyclerView = view.findViewById(R.id.recycler_view)
        findMatches()
        return view
    }

    private fun findMatches() {
        val users = Users()


        if (mMatches != null)
            mMatches.clear()

        for (user in users.USERS)
            mMatches.add(user)

        if (mainRvAdapter == null) {
            initRecyclerView()
        }

    }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: initialize RV")
        mRecyclerView.layoutManager = StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL)
        mainRvAdapter = MainRecyclerViewAdapter(requireContext(), mMatches)
        mRecyclerView.adapter = mainRvAdapter
    }

    companion object {
        private const val TAG = "HomeFragmentTAG"
        private const val NUM_COLUMNS = 2
    }
}