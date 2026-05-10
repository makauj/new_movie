package com.moviestream.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moviestream.core.database.dao.FavoriteDao
import com.moviestream.core.database.dao.MovieDao
import com.moviestream.core.database.dao.RecentlyViewedDao
import com.moviestream.core.database.dao.SearchHistoryDao
import com.moviestream.core.database.dao.UserProfileDao
import com.moviestream.core.database.dao.WatchListDao
import com.moviestream.core.database.dao.WatchProgressDao
import com.moviestream.core.database.entity.FavoriteEntity
import com.moviestream.core.database.entity.MovieEntity
import com.moviestream.core.database.entity.RecentlyViewedEntity
import com.moviestream.core.database.entity.SearchHistoryEntity
import com.moviestream.core.database.entity.UserProfileEntity
import com.moviestream.core.database.entity.WatchListEntity
import com.moviestream.core.database.entity.WatchProgressEntity

@Database(
    entities = [
        MovieEntity::class,
        FavoriteEntity::class,
        WatchProgressEntity::class,
        SearchHistoryEntity::class,
        RecentlyViewedEntity::class,
        UserProfileEntity::class,
        WatchListEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MovieStreamDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun watchProgressDao(): WatchProgressDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun recentlyViewedDao(): RecentlyViewedDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun watchListDao(): WatchListDao

    companion object {
        const val DATABASE_NAME = "moviestream_db"
    }
}
