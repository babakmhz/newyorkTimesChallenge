package com.android.babakmhz.newyorktimetopstories.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.android.babakmhz.newyorktimetopstories.R
import com.android.babakmhz.newyorktimetopstories.databinding.ActivityMainBinding
import com.android.babakmhz.newyorktimetopstories.utils.AppLogger
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

val tag = AppLogger.getTag(MainActivity::class.java)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.viewModel = viewModel
        activityMainBinding.executePendingBindings()
        setUpTabView()
        setupObservers()
    }

    private fun setUpTabView() {
        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(this).add("Top stories", MainFragment::class.java)
                .add("Bookmarks", BookmarksFragment::class.java).create()
        )
        val viewPager: ViewPager = findViewById(R.id.viewpager)
        viewpager.adapter = adapter
        val viewPagerTab: SmartTabLayout = findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    private fun setupObservers() {
        setUpFragmentObserver()
    }




    private fun setUpFragmentObserver() {
        val observer = Observer<Fragment> {
//            if (it != null) {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, it)
//                    .commitNow()
//            }
        }

        viewModel.currentFragment.observe(this, observer)
    }


}