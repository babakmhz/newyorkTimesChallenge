package com.android.babakmhz.newyorktimetopstories.data

import com.android.babakmhz.newyorktimetopstories.di.qualifier.ApiKey
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    @Inject
    @ApiKey
    lateinit var apikey:String

    override suspend fun getTopStories(): TopStories {
        return apiService.getTopStories(apikey)
    }
}