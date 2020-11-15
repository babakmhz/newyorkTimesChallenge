package com.android.babakmhz.newyorktimetopstories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.babakmhz.newyorktimetopstories.R
import com.android.babakmhz.newyorktimetopstories.data.Result
import com.android.babakmhz.newyorktimetopstories.databinding.FragmentMainBinding
import com.android.babakmhz.newyorktimetopstories.utils.LiveDataWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), callBack {


    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() =
            MainFragment()
    }

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main, container, false
            )
        fragmentMainBinding.viewModel = viewModel
        fragmentMainBinding.executePendingBindings()
        recyclerView = fragmentMainBinding.root.findViewById(R.id.recycler_items)
        return fragmentMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTopStoriesObserver()
    }


    private fun setupRecyclerAdapter(items: ArrayList<Result>) {
        if (items.isNotEmpty()) {
            val adapter = ItemsRecyclerAdapter(requireContext(), items, this)
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
    }

    private fun setupTopStoriesObserver() {
        viewModel.topStoriesLiveData.observe(viewLifecycleOwner, {
            when (it.responseRESPONSESTATUS) {
                LiveDataWrapper.RESPONSESTATUS.SUCCESS -> setupRecyclerAdapter(it.response!!.results)
            }
        })
    }

    private val fragmentsObserver = Observer<Fragment> {

    }

    override fun onItemClicked(story: Result) {
        viewModel.onStoryClicked(story)
    }
}