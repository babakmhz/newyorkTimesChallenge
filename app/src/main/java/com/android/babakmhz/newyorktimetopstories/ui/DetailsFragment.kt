package com.android.babakmhz.newyorktimetopstories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.babakmhz.newyorktimetopstories.R
import com.android.babakmhz.newyorktimetopstories.data.Result
import com.android.babakmhz.newyorktimetopstories.databinding.FragmentDetailsBinding
import com.android.babakmhz.newyorktimetopstories.utils.LiveDataWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class DetailsFragment : Fragment()  {

    companion object {
        fun newInstance() =
            DetailsFragment()
    }

    private  val viewModel: MainViewModel by viewModels()
    private lateinit var fragmentDetailsBinding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDetailsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_details, container, false
            )

        fragmentDetailsBinding.viewModel = viewModel
        fragmentDetailsBinding.executePendingBindings()
        return fragmentDetailsBinding.root
    }


    private val liveDataObserver = Observer<Result> {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}