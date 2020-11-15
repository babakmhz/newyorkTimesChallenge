package com.android.babakmhz.newyorktimetopstories.ui

import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.babakmhz.newyorktimetopstories.data.RepositoryHelper
import com.android.babakmhz.newyorktimetopstories.data.Result
import com.android.babakmhz.newyorktimetopstories.data.TopStories
import com.android.babakmhz.newyorktimetopstories.utils.AppLogger
import com.android.babakmhz.newyorktimetopstories.utils.LiveDataWrapper
import kotlinx.coroutines.*

class MainViewModel @ViewModelInject constructor(
    private val repositoryHelper: RepositoryHelper
) : ViewModel() {

    private val tag = AppLogger.getTag(MainViewModel::class.java)

    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private val job = SupervisorJob()
    private val mUiScope = CoroutineScope(mainDispatcher + job)

    private var _topStoriesLiveData = MutableLiveData<LiveDataWrapper<TopStories>>()
    val topStoriesLiveData: LiveData<LiveDataWrapper<TopStories>> = _topStoriesLiveData

    private var _bookmarks = MutableLiveData<List<Result>>()
    val bookmarks: LiveData<List<Result>> = _bookmarks
    private val listBookmarks =  arrayListOf<Result>()

    private var _selectedStory = MutableLiveData<Result>()
    val selectedStory: LiveData<Result> = _selectedStory


    private var _currentFragment = MutableLiveData<Fragment>()
    val currentFragment: LiveData<Fragment> = _currentFragment


    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    init {
        _currentFragment.value = MainFragment.newInstance()
        fetchTopStories()
    }


    fun addToBookmarks(story: Result) {
        listBookmarks.add(story)
        _bookmarks.postValue(listBookmarks)
    }

    fun removeBookmark(story: Result) {
        listBookmarks.remove(story)
        _bookmarks.postValue(listBookmarks)
    }

    private fun fetchTopStories() {
        //  fetching data from repo
        mUiScope.launch {
            _topStoriesLiveData.postValue(LiveDataWrapper.loading())
            withContext(Dispatchers.IO) {
                // fetching stories from db if there is any
                try {
                    val cache = repositoryHelper.getStoriesFromCache()
                    if (cache.isNotEmpty()) {
                        _topStoriesLiveData.postValue(LiveDataWrapper.success(TopStories(cache as ArrayList<Result>)))
                    }
                    // fetching stories from remote source
                    val result = repositoryHelper.getTopStories()
                    repositoryHelper.insertStoriesToDb(result.results)
                    _topStoriesLiveData.postValue(LiveDataWrapper.success(result))
                } catch (e: Exception) {
                    _topStoriesLiveData.postValue(LiveDataWrapper.error(e))
                    AppLogger.e("$tag $e")
                }


            }
        }
    }


    fun onStoryClicked(story: Result) {
        _selectedStory.postValue(story)
    }

    fun setCurrentFragment(fragment: Fragment) {
        _currentFragment.postValue(fragment)
    }


}
