package com.android.babakmhz.newyorktimetopstories.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class TopStories(@SerializedName("results") val results: ArrayList<Result>)

@Entity
data class Result(
    @ColumnInfo @SerializedName("title") val title: String,
    @ColumnInfo @SerializedName("abstract") val abstract: String,
    @ColumnInfo @SerializedName("url") val url: String,
    @ColumnInfo @SerializedName("published_date") val publishedDate: String,
    @Embedded @SerializedName("multimedia") val multimedia: ArrayList<MultiMedia>
)

@Entity
data class MultiMedia(@ColumnInfo @SerializedName("url") val url: String)