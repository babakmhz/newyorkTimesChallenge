package com.android.babakmhz.newyorktimetopstories.data

import com.android.babakmhz.newyorktimetopstories.utils.LiveDataWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("home.json")
    suspend fun getTopStories(@Query("api-key") apiKey:String): TopStories
}