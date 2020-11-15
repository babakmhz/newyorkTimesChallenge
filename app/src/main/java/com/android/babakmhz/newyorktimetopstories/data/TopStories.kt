package com.android.babakmhz.newyorktimetopstories.data

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class TopStories(@SerializedName("results") var results: ArrayList<Result>)

@Entity(tableName = "Result")
data class Result(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo @SerializedName("title") var title: String = "",
    @ColumnInfo @SerializedName("abstract") var abstract: String = "",
    @ColumnInfo @SerializedName("url") var url: String = "",
    @ColumnInfo @SerializedName("published_date") var publishedDate: String = "",
    @SerializedName("multimedia") var multimedia: ArrayList<MultiMedia> = arrayListOf()
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
    @PrimaryKey(autoGenerate = true) var mediaId: Int,
    @ColumnInfo @SerializedName("url") var url: String = ""
)

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
)

