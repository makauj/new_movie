package com.moviestream.core.database.di

import android.content.Context
import androidx.room.Room
import com.moviestream.core.database.MovieStreamDatabase
import com.moviestream.core.database.dao.FavoriteDao
import com.moviestream.core.database.dao.MovieDao
import com.moviestream.core.database.dao.RecentlyViewedDao
import com.moviestream.core.database.dao.SearchHistoryDao
import com.moviestream.core.database.dao.UserProfileDao
import com.moviestream.core.database.dao.WatchListDao
import com.moviestream.core.database.dao.WatchProgressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieStreamDatabase(
        @ApplicationContext context: Context
    ): MovieStreamDatabase = Room.databaseBuilder(
        context,
        MovieStreamDatabase::class.java,
        MovieStreamDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieStreamDatabase): MovieDao = database.movieDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: MovieStreamDatabase): FavoriteDao = database.favoriteDao()

    @Provides
    @Singleton
    fun provideWatchProgressDao(database: MovieStreamDatabase): WatchProgressDao = database.watchProgressDao()

    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: MovieStreamDatabase): SearchHistoryDao = database.searchHistoryDao()

    @Provides
    @Singleton
    fun provideRecentlyViewedDao(database: MovieStreamDatabase): RecentlyViewedDao = database.recentlyViewedDao()

    @Provides
    @Singleton
    fun provideUserProfileDao(database: MovieStreamDatabase): UserProfileDao = database.userProfileDao()

    @Provides
    @Singleton
    fun provideWatchListDao(database: MovieStreamDatabase): WatchListDao = database.watchListDao()
}
