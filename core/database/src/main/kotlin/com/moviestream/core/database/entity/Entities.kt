package com.moviestream.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val poster: String?,
    val backdrop: String?,
    val rating: Double?,
    val releaseDate: String?,
    val genres: String?, // JSON serialized list
    val runtime: Int?,
    val director: String?,
    val cast: String?, // JSON serialized list
    val type: String?, // "movie" or "show"
    val imdbRating: String?,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val movieId: String,
    val title: String,
    val poster: String?,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "watch_progress")
data class WatchProgressEntity(
    @PrimaryKey
    val movieId: String,
    val watchedDurationMs: Long,
    val totalDurationMs: Long,
    val lastWatchedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey
    val query: String,
    val searchedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "recently_viewed")
data class RecentlyViewedEntity(
    @PrimaryKey
    val movieId: String,
    val title: String,
    val poster: String?,
    val viewedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val userId: String,
    val username: String,
    val email: String,
    val profileImage: String?,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "watch_list")
data class WatchListEntity(
    @PrimaryKey
    val movieId: String,
    val title: String,
    val poster: String?,
    val addedAt: Long = System.currentTimeMillis()
)
