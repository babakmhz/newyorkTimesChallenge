package com.android.babakmhz.newyorktimetopstories.data

import androidx.annotation.ColorInt
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class TopStories(@SerializedName("results") var results: ArrayList<Result>)

@Entity(
    tableName = "Result"
)
data class Result(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo @SerializedName("title") var title: String = "",
    @ColumnInfo @SerializedName("abstract") var abstract: String = "",
    @ColumnInfo @SerializedName("url") var url: String = "",
    @ColumnInfo @SerializedName("published_date") var publishedDate: String = "",
    @Ignore @SerializedName("multimedia") var multimedia: ArrayList<MultiMedia> = arrayListOf(),
    @ColumnInfo var bookmarked:Boolean = false
)

data class StoriesWithMultimedia(
    @Embedded(prefix = "media_") var story: Result,
    @Relation(
        parentColumn = "id",
        entityColumn = "mediaId"
    ) var multimedia: List<MultiMedia>
)


@Entity(tableName = "MultiMedia")
data class MultiMedia(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) var mediaId: Int,
    @ColumnInfo @SerializedName("url") var url: String = ""
)

class TypeConverters {
    @TypeConverter
    fun convertMultimedia(listOfMedia: List<MultiMedia>): String? {
        return Gson().toJson(listOfMedia.toString())
    }

}

data class StoriesBookmarked(
    @Embedded(prefix = "bookmark") val story: Result,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookmark_id"
    ) var bookmark: Bookmark
)

@Entity(tableName = "Bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) var bookmark_id: Int,
    @Embedded val story :Result
)

