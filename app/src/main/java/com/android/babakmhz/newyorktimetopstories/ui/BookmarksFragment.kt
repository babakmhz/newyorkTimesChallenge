package com.android.babakmhz.newyorktimetopstories.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.babakmhz.newyorktimetopstories.R
import com.android.babakmhz.newyorktimetopstories.data.Result
import com.android.babakmhz.newyorktimetopstories.databinding.FragmentMainBinding
import com.android.babakmhz.newyorktimetopstories.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_stories.*

@AndroidEntryPoint
class BookmarksFragment : Fragment(), callBack {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fragmentMainBinding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
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
//        image_bookmark.visibility = View.GONE
        return fragmentMainBinding.root
    }

    private fun setupRecyclerAdapter(items: ArrayList<Result>) {
        if (items.isNotEmpty()) {
            val adapter = ItemsRecyclerAdapter(requireContext(), items, this)
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bookmarks.observe(viewLifecycleOwner, bookmarksObserver)
    }

    private val bookmarksObserver = Observer<List<Result>> {
        if (it.isNotEmpty()) {
            setupRecyclerAdapter(it as ArrayList<Result>)
        }

    }

    override fun onItemClicked(story: Result) {
        try {
            viewModel.onStoryClicked(story)
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(INTENT_TITLE_KEY, story.title)
            intent.putExtra(INTENT_DATE_KEY, story.publishedDate)
            intent.putExtra(INTENT_URL_KEY, story.url)
            intent.putExtra(INTENT_IMAGE_KEY, story.multimedia[0].url)
            intent.putExtra(INTENT_ABSTRACT_KEY, story.abstract)
            activity?.startActivity(intent)
        } catch (e: Exception) {
            Snackbar.make(fragmentMainBinding.root, "updating data...", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onBookmarkClicked(added: Boolean, story: Result) {

    }
}