package com.android.babakmhz.newyorktimetopstories.data

import androidx.room.*


@Dao
interface TopStoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStoriesToDb(result: List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmarkStory(story: Result)

    @Transaction
    @Query("SELECT * from RESULT")
    suspend fun getStoriesFromCache(): List<Result>

    @Transaction
    @Query("SELECT * FROM BOOKMARK")
    suspend fun getBookmarks(): List<Bookmark>

    @Transaction
    @Query("DELETE FROM Bookmark WHERE bookmark_id = :id")
    suspend fun removeBookmark(id: Int)

}