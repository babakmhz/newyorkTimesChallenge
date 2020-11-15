package com.android.babakmhz.newyorktimetopstories.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.babakmhz.newyorktimetopstories.utils.DATABASE_SCHEMA_VERSION

@Database(
        entities = [Result::class,MultiMedia::class,Bookmark::class],
        version = DATABASE_SCHEMA_VERSION,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
        abstract fun topStoriesDao():TopStoriesDao

}
