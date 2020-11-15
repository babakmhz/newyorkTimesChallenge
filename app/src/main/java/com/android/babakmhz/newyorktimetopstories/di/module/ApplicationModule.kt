package com.android.babakmhz.newyorktimetopstories.di.module

import android.content.Context
import androidx.room.Room
import com.android.babakmhz.newyorktimetopstories.BuildConfig
import com.android.babakmhz.newyorktimetopstories.data.*
import com.android.babakmhz.newyorktimetopstories.di.qualifier.ApiKey
import com.android.babakmhz.newyorktimetopstories.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @ApiKey
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideRepositoryHelper(repositoryImpl: RepositoryImpl): RepositoryHelper = repositoryImpl

    @Provides
    @Singleton
    fun provideTopStoriesDao(
        @ApplicationContext context: Context
    ): TopStoriesDao = Room.databaseBuilder(
        context, AppDatabase::class.java,
        DATABASE_NAME
    ).build().topStoriesDao()

}