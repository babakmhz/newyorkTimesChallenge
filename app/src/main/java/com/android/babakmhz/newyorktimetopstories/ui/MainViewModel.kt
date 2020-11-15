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

    private var _selectedStory = MutableLiveData<Result>()
    val selectedStory: LiveData<Result> = _selectedStory

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _currentFragment = MutableLiveData<Fragment>()
    val currentFragment: LiveData<Fragment> = _currentFragment

    init {
        _currentFragment.value = MainFragment.newInstance()
        fetchTopStories()
    }

    private fun fetchTopStories() {
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
                    _loading.postValue(false)
                } catch (e: Exception) {
                    _loading.postValue(false)
                    _topStoriesLiveData.postValue(LiveDataWrapper.error(e))
                    AppLogger.e("$tag $e")
                }


            }
        }
    }

    fun bookmarkStory(story: Result) {
        mUiScope.launch {
            withContext(Dispatchers.IO) {
                repositoryHelper.bookmarkStory(story)
            }
        }
    }

    fun removeStoryFromBookmarks(id: Int) {
        mUiScope.launch {
            withContext(Dispatchers.IO) {
                repositoryHelper.removeBookmark(id)
            }
        }
    }

    fun onStoryClicked(story: Result) {

    }

    fun setCurrentFragment(fragment:Fragment){
        _currentFragment.postValue(fragment)
    }


}
