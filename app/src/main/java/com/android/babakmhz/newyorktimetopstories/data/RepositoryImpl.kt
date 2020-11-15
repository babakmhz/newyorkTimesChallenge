package com.android.babakmhz.newyorktimetopstories.data

import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val topStoriesDao: TopStoriesDao,
) : RepositoryHelper {

    override suspend fun getTopStories() = apiHelper.getTopStories()

    override suspend fun insertStoriesToDb(result: List<Result>) {
        topStoriesDao.insertStoriesToDb(result)
    }

    override suspend fun bookmarkStory(story: Result) {
        topStoriesDao.bookmarkStory(story)
    }

    override suspend fun getStoriesFromCache(): List<Result> {
        return topStoriesDao.getStoriesFromCache()
    }

    override suspend fun getBookmarks(): List<Bookmark> {
        return topStoriesDao.getBookmarks()
    }

    override suspend fun removeBookmark(id: Int) {
        topStoriesDao.removeBookmark(id)
    }
}