package com.moviestream.core.common.repository

import com.moviestream.core.common.Result
import com.moviestream.core.common.model.Movie
import com.moviestream.core.common.model.MovieDetails
import com.moviestream.core.common.model.Genre

interface MovieRepository {
    suspend fun getTrendingMovies(page: Int = 1): Result<List<Movie>>
    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>>
    suspend fun getTopRatedMovies(page: Int = 1): Result<List<Movie>>
    suspend fun getNewReleases(page: Int = 1): Result<List<Movie>>
    suspend fun getMovieDetails(movieId: String): Result<MovieDetails>
    suspend fun searchMovies(query: String, page: Int = 1): Result<List<Movie>>
    suspend fun getGenres(): Result<List<Genre>>
    suspend fun getMoviesByGenre(genre: String, page: Int = 1): Result<List<Movie>>
    suspend fun getSimilarMovies(movieId: String): Result<List<Movie>>
    suspend fun getTrendingTvShows(page: Int = 1): Result<List<Movie>>
    suspend fun getPopularTvShows(page: Int = 1): Result<List<Movie>>
}
