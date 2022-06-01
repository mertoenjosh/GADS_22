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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mertoenjosh.tabiandating.adapters.MainRecyclerViewAdapter
import com.mertoenjosh.tabiandating.models.User
import com.mertoenjosh.tabiandating.util.Users

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val matches: ArrayList<User> = ArrayList()
    private var mainRvAdapter: MainRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        Log.d(TAG, "onCreateView: Started.")
        recyclerView = view.findViewById(R.id.recycler_view)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)
        findMatches()
        return view
    }

    private fun findMatches() {
        val users = Users()


        if (matches != null)
            matches.clear()

        for (user in users.USERS)
            matches.add(user)

        if (mainRvAdapter == null) {
            initRecyclerView()
        }

    }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: initialize RV")
        recyclerView.layoutManager = StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL)
        mainRvAdapter = MainRecyclerViewAdapter(requireContext(), matches)
        recyclerView.adapter = mainRvAdapter
    }

    fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    companion object {
        private const val TAG = "HomeFragmentTAG"
        private const val NUM_COLUMNS = 2
    }

    override fun onRefresh() {
        findMatches()
        onLoadComplete()
    }

    private fun onLoadComplete() {
        mainRvAdapter?.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }
}