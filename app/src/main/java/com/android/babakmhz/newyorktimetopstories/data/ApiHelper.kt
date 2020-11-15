package com.android.babakmhz.newyorktimetopstories.data

import com.android.babakmhz.newyorktimetopstories.utils.LiveDataWrapper

interface ApiHelper {
    suspend fun getTopStories():TopStories
}