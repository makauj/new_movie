package com.moviestream.core.network.api

import com.moviestream.core.network.model.MovieDetailResponse
import com.moviestream.core.network.model.MovieListResponse
import com.moviestream.core.network.model.SearchResponse
import com.moviestream.core.network.model.GenreListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Cineby API Service Interface
 * Documentation: https://www.vidking.net/#documentation
 */
interface CinebytApi {

    // Home Screen Endpoints
    
    @GET("api/home/trending")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    @GET("api/home/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    @GET("api/home/topRated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    @GET("api/home/newReleases")
    suspend fun getNewReleases(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    // Search Endpoints

    @GET("api/search")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("type") type: String = "all", // "movie", "tv", "all"
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): SearchResponse

    @GET("api/search/suggestions")
    suspend fun getSearchSuggestions(
        @Query("query") query: String,
        @Query("limit") limit: Int = 10
    ): List<String>

    @GET("api/genres")
    suspend fun getGenres(): GenreListResponse

    @GET("api/genre/{genre}")
    suspend fun getMoviesByGenre(
        @Path("genre") genre: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    // Movie Details

    @GET("api/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: String
    ): MovieDetailResponse

    @GET("api/movie/{id}/trailer")
    suspend fun getMovieTrailer(
        @Path("id") movieId: String
    ): TrailerResponse

    @GET("api/movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") movieId: String,
        @Query("limit") limit: Int = 10
    ): MovieListResponse

    // TV Shows

    @GET("api/tv/trending")
    suspend fun getTrendingTvShows(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    @GET("api/tv/popular")
    suspend fun getPopularTvShows(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse

    @GET("api/tv/{id}/season/{seasonNumber}")
    suspend fun getSeasonDetails(
        @Path("id") tvShowId: String,
        @Path("seasonNumber") seasonNumber: Int
    ): MovieDetailResponse

    // Filtering & Discovery

    @GET("api/discover")
    suspend fun discoverMovies(
        @Query("genre") genre: String? = null,
        @Query("year") year: Int? = null,
        @Query("sort") sort: String = "popularity",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): MovieListResponse
}

@kotlinx.serialization.Serializable
data class TrailerResponse(
    val url: String,
    val thumbnail: String?
)
