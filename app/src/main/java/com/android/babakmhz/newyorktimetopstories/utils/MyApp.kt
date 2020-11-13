package com.android.babakmhz.newyorktimetopstories.utils

import android.app.Application
import com.android.babakmhz.newyorktimetopstories.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            AppLogger.init()


    }

}