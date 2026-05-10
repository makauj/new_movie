package com.moviestream.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moviestream.core.database.entity.FavoriteEntity
import com.moviestream.core.database.entity.MovieEntity
import com.moviestream.core.database.entity.RecentlyViewedEntity
import com.moviestream.core.database.entity.SearchHistoryEntity
import com.moviestream.core.database.entity.UserProfileEntity
import com.moviestream.core.database.entity.WatchListEntity
import com.moviestream.core.database.entity.WatchProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: String): MovieEntity?

    @Query("SELECT * FROM movies LIMIT :limit OFFSET :offset")
    suspend fun getMovies(limit: Int, offset: Int): List<MovieEntity>

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE movieId = :movieId")
    suspend fun getFavorite(movieId: String): FavoriteEntity?

    @Query("DELETE FROM favorites WHERE movieId = :movieId")
    suspend fun removeFavoriteById(movieId: String)

    @Query("SELECT COUNT(*) FROM favorites WHERE movieId = :movieId")
    suspend fun isFavorite(movieId: String): Int
}

@Dao
interface WatchProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWatchProgress(progress: WatchProgressEntity)

    @Query("SELECT * FROM watch_progress WHERE movieId = :movieId")
    suspend fun getWatchProgress(movieId: String): WatchProgressEntity?

    @Query("SELECT * FROM watch_progress ORDER BY lastWatchedAt DESC LIMIT :limit")
    suspend fun getRecentWatches(limit: Int = 10): List<WatchProgressEntity>

    @Delete
    suspend fun deleteWatchProgress(progress: WatchProgressEntity)

    @Query("DELETE FROM watch_progress WHERE movieId = :movieId")
    suspend fun deleteWatchProgressById(movieId: String)
}

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchHistory(search: SearchHistoryEntity)

    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC LIMIT :limit")
    fun getSearchHistory(limit: Int = 20): Flow<List<SearchHistoryEntity>>

    @Delete
    suspend fun deleteSearchHistory(search: SearchHistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()

    @Query("SELECT DISTINCT query FROM search_history ORDER BY searchedAt DESC LIMIT :limit")
    fun getSearchQueries(limit: Int = 10): Flow<List<String>>
}

@Dao
interface RecentlyViewedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecentlyViewed(recently: RecentlyViewedEntity)

    @Query("SELECT * FROM recently_viewed ORDER BY viewedAt DESC LIMIT :limit")
    fun getRecentlyViewed(limit: Int = 20): Flow<List<RecentlyViewedEntity>>

    @Delete
    suspend fun deleteRecentlyViewed(recently: RecentlyViewedEntity)

    @Query("DELETE FROM recently_viewed")
    suspend fun clearRecentlyViewed()
}

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE userId = :userId")
    fun getUserProfile(userId: String): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getCurrentUserProfile(): UserProfileEntity?

    @Delete
    suspend fun deleteUserProfile(profile: UserProfileEntity)

    @Update
    suspend fun updateUserProfile(profile: UserProfileEntity)
}

@Dao
interface WatchListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(item: WatchListEntity)

    @Query("SELECT * FROM watch_list ORDER BY addedAt DESC")
    fun getWatchList(): Flow<List<WatchListEntity>>

    @Delete
    suspend fun removeFromWatchList(item: WatchListEntity)

    @Query("DELETE FROM watch_list WHERE movieId = :movieId")
    suspend fun removeFromWatchListById(movieId: String)

    @Query("SELECT COUNT(*) FROM watch_list WHERE movieId = :movieId")
    suspend fun isInWatchList(movieId: String): Int

    @Query("DELETE FROM watch_list")
    suspend fun clearWatchList()
}
